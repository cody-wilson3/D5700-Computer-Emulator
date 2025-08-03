package org.example.Instructions

import org.example.CPU

class SetTInstruction(private val raw: Int) : Instruction(raw) {
    private var value = 0

    override fun decode() {
        value = (raw shr 4) and 0xFF  // Get the middle byte (bb)
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()
        CPU.T = value
        CPU.incrementCounter()
    }
}
