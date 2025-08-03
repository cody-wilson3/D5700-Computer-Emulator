package org.example.Instructions

import org.example.CPU

class ReadTInstruction(private val raw: Int) : Instruction(raw) {
    private var rX = 0

    override fun decode() {
        rX = (raw shr 8) and 0xF  // Extract rX from bits 11–8
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()
        CPU.setRegister(rX, CPU.T.toByte())
        CPU.incrementCounter()
    }
}
