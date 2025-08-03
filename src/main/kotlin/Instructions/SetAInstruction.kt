package org.example.Instructions

import org.example.CPU

class SetAInstruction(private val raw: Int) : Instruction(raw) {
    private var value = 0

    override fun decode() {
        value = raw and 0x0FFF // Extract the last 12 bits (aaa)
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()
        CPU.A = value
        CPU.incrementCounter()
    }
}
