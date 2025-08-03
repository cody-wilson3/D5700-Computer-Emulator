package org.example.Instructions

import org.example.CPU

class DrawInstruction(private val raw: Int) : Instruction(raw) {
    private var rX = 0
    private var rY = 0
    private var rZ = 0

    override fun decode() {
        rX = (raw shr 8) and 0xF
        rY = (raw shr 4) and 0xF
        rZ = raw and 0xF
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()

        var asciiValue = CPU.getRegisterVal(rX).toInt() and 0xFF

        if (asciiValue > 0x7F) {
            error("DRAW: ASCII value $asciiValue in r$rX exceeds 0x7F")
        }

        // Calculate the address in screen RAM
        // Screen is 8x8, so address = row * 8 + column
        if (rY !in 0..7 || rZ !in 0..7) {
            error("DRAW: Row $rY or column $rZ out of bounds (0-7)")
        }

        val address = rY * 8 + rZ

//        if (cpu.M) {
//            asciiValue = asciiValue.toString().toInt(16)
//        }

        // Screen RAM starts at a known offset in memory or cpu.screenRam?
        // Assuming 'memory' here is screen RAM (64 bytes)
        memory[address] = asciiValue.toByte()

        cpu.screen.drawChar(rY, rZ, asciiValue.toChar())
        cpu.screen.render()

        CPU.incrementCounter()
    }

}
