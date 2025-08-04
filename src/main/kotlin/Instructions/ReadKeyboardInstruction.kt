package org.example.Instructions

import org.example.CPU

class ReadKeyboardInstruction(private val raw: Int, private val inputProvider: () -> String? = { readLine() }) : Instruction(raw) {
    private var rX = 0

    override fun decode() {
        rX = (raw shr 8) and 0xF
    }

    override fun execute(cpu: CPU, memory: ByteArray) {
        decode()

        print("Enter hex input (0–FF): ")
        val input = readLine()?.trim()?.take(2) ?: ""

        val value = try {
            if (input.isEmpty()) 0
            else input.toInt(16).coerceIn(0, 0xFF)
        } catch (e: NumberFormatException) {
            println("Invalid input: storing 0 in r$rX")
            0
        }

        CPU.setRegister(rX, value.toByte())
        CPU.incrementCounter()
    }
}
