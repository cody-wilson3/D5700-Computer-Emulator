package org.example.Instructions

import org.example.CPU

class SkipEqualInstruction(private val raw: Int) : Instruction(raw) {
    private var rX = 0
    private var rY = 0

    override fun decode() {
        rX = (raw shr 8) and 0xF
        rY = (raw shr 4) and 0xF
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()

        val xVal = CPU.getRegisterVal(rX)
        val yVal = CPU.getRegisterVal(rY)

        if (xVal == yVal) {
            CPU.incrementCounter()
            CPU.incrementCounter() // Skip next instruction
            CPU.incrementCounter()
        } else {
            CPU.incrementCounter() // Continue as normal
        }
    }
}
