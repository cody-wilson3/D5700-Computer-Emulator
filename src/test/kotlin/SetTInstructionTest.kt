package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class SetTInstructionTest {

    @BeforeTest
    fun resetCPU() {
        CPU.T = 0
        CPU.setP(0)
    }

    @Test
    fun `sets T to zero`() {
        val raw = 0xB000  // middle byte = 0x00
        val instr = SetTInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(0, CPU.T)
    }

    @Test
    fun `sets T to max 255`() {
        val raw = 0xBFF0  // middle byte = 0xFF (bits 11-4)
        val instr = SetTInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(255, CPU.T)
    }

    @Test
    fun `sets T to arbitrary value`() {
        val raw = 0xB4A0  // middle byte = 0x4A = 74 decimal
        val instr = SetTInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(74, CPU.T)
    }

    @Test
    fun `increments program counter`() {
        val initialPC = CPU.getP()
        val raw = 0xB123  // some value with middle byte 0x12
        val instr = SetTInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(initialPC + 1, CPU.getP())
    }
}
