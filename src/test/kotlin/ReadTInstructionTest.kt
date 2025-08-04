package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class ReadTInstructionTest {

    @BeforeTest
    fun setup() {
        // Reset the CPU state for each test
        for (i in 0..7) {
            CPU.setRegister(i, 0)
        }
        CPU.T = 0
        CPU.setP(0)
    }

    @Test
    fun `copies T value into rX`() {
        CPU.T = 42
        val instr = ReadTInstruction((0x7 shl 12) or (0x3 shl 8))  // opcode 0x7, rX = 3

        instr.execute(CPU, CPU.memory)

        assertEquals(42.toByte(), CPU.getRegisterVal(3))
    }

    @Test
    fun `copies T = 0`() {
        CPU.T = 0
        val instr = ReadTInstruction((0x7 shl 12) or (0x1 shl 8))  // rX = 1

        instr.execute(CPU, CPU.memory)

        assertEquals(0.toByte(), CPU.getRegisterVal(1))
    }

}
