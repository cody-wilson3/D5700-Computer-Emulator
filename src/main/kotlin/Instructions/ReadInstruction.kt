package org.example.Instructions

import org.example.CPU

class ReadInstruction(private val raw: Int) : Instruction(raw) {
    private var rX = 0

    override fun decode() {
        rX = (raw shr 8) and 0xF
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()

        // values are interpreted as base 16 from rom but stored in memory as decimal
        val address = CPU.A
        var whatever = CPU.rom[address]
        val value: Int = when {
            CPU.M && address in CPU.rom.indices -> CPU.rom[address].toInt(16)
            !CPU.M && address in memory.indices -> memory[address].toInt() and 0xFF
            else -> 0
        }

        // value here is a base 10
        CPU.setRegister(rX, value.toByte())
        CPU.incrementCounter()
    }
}
