package org.example.Instructions

import org.example.CPU

class JumpInstruction(private val raw: Int) : Instruction(raw) {
    private var address = 0

    override fun decode() {
        address = raw and 0x0FFF // Last 12 bits
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()

        if (address % 2 != 0) {
            error("JUMP failed: address $address is not divisible by 2")
        }

        CPU.setP(address)
    }
}
