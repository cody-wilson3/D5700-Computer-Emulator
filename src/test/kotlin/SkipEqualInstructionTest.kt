package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class SkipEqualInstructionTest {

    @BeforeTest
    fun resetCPU() {
        // Reset CPU registers and program counter before each test
        for (i in 0..7) {
            CPU.setRegister(i, 0)
        }
        CPU.setP(0)
    }

    @Test
    fun `increments PC by 3 when registers are equal`() {
        CPU.setRegister(2, 42)
        CPU.setRegister(3, 42)
        val raw = (0x8 shl 12) or (2 shl 8) or (3 shl 4)
        val instr = SkipEqualInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(3, CPU.getP())
    }

    @Test
    fun `increments PC by 1 when registers are not equal`() {
        CPU.setRegister(1, 10)
        CPU.setRegister(4, 20)
        val raw = (0x8 shl 12) or (1 shl 8) or (4 shl 4)
        val instr = SkipEqualInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(1, CPU.getP())
    }

    @Test
    fun `works correctly if registers contain zero`() {
        CPU.setRegister(0, 0)
        CPU.setRegister(1, 0)
        val raw = (0x8 shl 12) or (0 shl 8) or (1 shl 4)
        val instr = SkipEqualInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(3, CPU.getP())
    }

    @Test
    fun `increments program counter correctly when values differ by one`() {
        CPU.setRegister(5, 100)
        CPU.setRegister(6, 101)
        val raw = (0x8 shl 12) or (5 shl 8) or (6 shl 4)
        val instr = SkipEqualInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(1, CPU.getP())
    }
}
