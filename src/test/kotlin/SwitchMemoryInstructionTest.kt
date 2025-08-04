package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class SwitchMemoryInstructionTest {

    @BeforeTest
    fun resetCPU() {
        CPU.M = false
        CPU.setP(0)
    }

    @Test
    fun `toggles M from false to true`() {
        CPU.M = false
        val raw = 0x7000 // opcode 7 (SwitchMemoryInstruction), other bits don't matter here
        val instr = SwitchMemoryInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertTrue(CPU.M)
        assertEquals(1, CPU.getP())
    }

    @Test
    fun `toggles M from true to false`() {
        CPU.M = true
        val raw = 0x7000
        val instr = SwitchMemoryInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertFalse(CPU.M)
        assertEquals(1, CPU.getP())
    }

    @Test
    fun `multiple toggles flips M multiple times`() {
        CPU.M = false
        val raw = 0x7000
        val instr = SwitchMemoryInstruction(raw)

        instr.execute(CPU, CPU.memory)
        assertTrue(CPU.M)

        instr.execute(CPU, CPU.memory)
        assertFalse(CPU.M)

        instr.execute(CPU, CPU.memory)
        assertTrue(CPU.M)

        assertEquals(3, CPU.getP())
    }
}
