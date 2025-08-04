package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class ReadKeyboardInstructionTest {

    @BeforeTest
    fun setup() {
        CPU.setRegister(1, 0)
        CPU.setP(0)
    }

    @Test
    fun `empty input stores 0`() {
        val instr = ReadKeyboardInstruction(
            raw = (0x6 shl 12) or (0x1 shl 8),
            inputProvider = { "" }
        )

        instr.execute(CPU, CPU.memory)

        assertEquals(0.toByte(), CPU.getRegisterVal(1))
    }

    @Test
    fun `invalid hex input stores 0`() {
        val instr = ReadKeyboardInstruction(
            raw = (0x6 shl 12) or (0x1 shl 8),
            inputProvider = { "ZZ" }
        )

        instr.execute(CPU, CPU.memory)

        assertEquals(0.toByte(), CPU.getRegisterVal(1))
    }

}
