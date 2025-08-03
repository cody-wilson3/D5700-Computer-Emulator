package org.example.Instructions

import org.example.CPU

class StoreInstruction(private val raw: Int) : Instruction(raw) {
    private var registerIndex = 0
    private var value = 0

    override fun decode() {
        registerIndex = (raw shr 8) and 0xF     // Get register index from 2nd nibble
        value = raw and 0xFF        // Get last byte (bb)
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()

        CPU.setRegister(registerIndex, value.toByte())
        CPU.incrementCounter()
    }
}
