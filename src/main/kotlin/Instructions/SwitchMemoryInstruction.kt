package org.example.Instructions

import org.example.CPU

class SwitchMemoryInstruction(raw: Int) : Instruction(raw) {

    override fun decode() {
        // Not needed
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        CPU.M = !CPU.M
        CPU.incrementCounter()
    }
}
