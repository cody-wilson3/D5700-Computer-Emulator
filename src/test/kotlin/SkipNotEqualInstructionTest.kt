package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class SkipNotEqualInstructionTest {

    @BeforeTest
    fun resetCPU() {
        for (i in 0..7) {
            CPU.setRegister(i, 0)
        }
        CPU.setP(0)
    }

    @Test
    fun `increments PC by 2 when registers are not equal`() {
        CPU.setRegister(2, 42)
        CPU.setRegister(3, 43)
        val raw = (0x9 shl 12) or (2 shl 8) or (3 shl 4)
        val instr = SkipNotEqualInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(2, CPU.getP())
    }

    @Test
    fun `increments PC by 1 when registers are equal`() {
        CPU.setRegister(1, 10)
        CPU.setRegister(4, 10)
        val raw = (0x9 shl 12) or (1 shl 8) or (4 shl 4)
        val instr = SkipNotEqualInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(1, CPU.getP())
    }

    @Test
    fun `handles zero values correctly when registers not equal`() {
        CPU.setRegister(0, 0)
        CPU.setRegister(1, 1)
        val raw = (0x9 shl 12) or (0 shl 8) or (1 shl 4)
        val instr = SkipNotEqualInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(2, CPU.getP())
    }

    @Test
    fun `handles zero values correctly when registers equal`() {
        CPU.setRegister(0, 0)
        CPU.setRegister(1, 0)
        val raw = (0x9 shl 12) or (0 shl 8) or (1 shl 4)
        val instr = SkipNotEqualInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(1, CPU.getP())
    }
}
