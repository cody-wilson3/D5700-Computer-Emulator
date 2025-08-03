package org.example.Instructions

import org.example.CPU

class WriteInstruction(private val raw: Int) : Instruction(raw) {
    private var rX = 0

    override fun decode() {
        rX = (raw shr 8) and 0xF
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()

        val address = CPU.A
        val value = CPU.getRegisterVal(rX).toInt() and 0xFF // Ensure 8-bit

        if (CPU.M) {
            // Attempt to write to ROM (future-proofing)
            if (address in CPU.rom.indices) {
                CPU.rom[address] = value.toString()
            } else {
                println("WRITE to ROM failed: address $address out of bounds")
            }
        } else {
            // Write to RAM
            if (address in memory.indices) {
                memory[address] = value.toByte()
            } else {
                println("WRITE to RAM failed: address $address out of bounds")
            }
        }

        CPU.incrementCounter()
    }
}
