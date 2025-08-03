package org.example.Instructions

import org.example.CPU

class AddInstruction(private val raw: Int) : Instruction(raw) {
    private var registerIndex = 0
    private var value = 0

    override fun decode() {
        registerIndex = raw and 0xF
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        val rX = (raw shr 8) and 0xF
        val rY = (raw shr 4) and 0xF
        val rZ = raw and 0xF

        val xVal = CPU.getRegisterVal(rX)
        val yVal = CPU.getRegisterVal(rY)
        value = (xVal + yVal) and 0xFF

        CPU.setRegister(rZ, value.toByte())
        CPU.incrementCounter()
    }
}