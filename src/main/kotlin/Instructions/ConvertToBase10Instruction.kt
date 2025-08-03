package org.example.Instructions

import org.example.CPU

class ConvertToBase10Instruction(private val raw: Int) : Instruction(raw) {
    private var rX = 0

    override fun decode() {
        rX = (raw shr 8) and 0xF
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()

        val value = CPU.getRegisterVal(rX).toInt() and 0xFF  // Get byte value from rX

        val hundreds = value / 100
        val tens = (value / 10) % 10
        val ones = value % 10

        CPU.memory[CPU.A] = hundreds.toByte()
        CPU.memory[CPU.A + 1] = tens.toByte()
        CPU.memory[CPU.A + 2] = ones.toByte()

        CPU.incrementCounter()
    }
}
