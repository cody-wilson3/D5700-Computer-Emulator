package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class JumpInstructionTest {

    @BeforeTest
    fun resetCPU() {
        CPU.setP(0)
    }

    @Test
    fun `jumps to valid even address`() {
        val targetAddress = 0x02A  // 42 in decimal, even
        val raw = (0x1 shl 12) or targetAddress
        val instr = JumpInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(targetAddress, CPU.getP())
    }

    @Test
    fun `fails to jump to odd address`() {
        val targetAddress = 0x045  // 69 in decimal, odd
        val raw = (0x1 shl 12) or targetAddress
        val instr = JumpInstruction(raw)

        val exception = assertFailsWith<IllegalStateException> {
            instr.execute(CPU, CPU.memory)
        }

        assertTrue(exception.message!!.contains("not divisible by 2"))
        assertEquals(0, CPU.getP(), "Program counter should remain unchanged")
    }

    @Test
    fun `zero address jump is valid`() {
        val raw = (0x1 shl 12) or 0x000
        val instr = JumpInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(0, CPU.getP())
    }

    @Test
    fun `max valid address jump`() {
        val rawAddress = 0xFFE  // 4094, max even 12-bit
        val raw = (0x1 shl 12) or rawAddress
        val instr = JumpInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(rawAddress, CPU.getP())
    }
}
