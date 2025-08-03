package org.example.Instructions

import org.example.CPU

class ConvertByteToAsciiInstruction(private val raw: Int) : Instruction(raw) {
    private var rX = 0
    private var rY = 0

    override fun decode() {
        rX = (raw shr 8) and 0xF
        rY = (raw shr 4) and 0xF
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()

        val value = CPU.getRegisterVal(rX).toInt() and 0xFF

        if (value > 0xF) {
            error("CONVERT_BYTE_TO_ASCII: Value in r$rX ($value) exceeds 0xF")
        }

        // Convert hex digit (0-F) to ASCII:
        // 0-9 -> '0' (0x30) + digit
        // A-F -> 'A' (0x41) + digit - 10
        val ascii = if (value <= 9) {
            0x30 + value
        } else {
            0x41 + (value - 10)
        }

        CPU.setRegister(rY, ascii.toByte())
        CPU.incrementCounter()
    }
}
