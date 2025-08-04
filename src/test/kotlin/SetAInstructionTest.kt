package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class SetAInstructionTest {

    @BeforeTest
    fun resetCPU() {
        CPU.A = 0
        CPU.setP(0)
    }

    @Test
    fun `sets A to minimum value 0`() {
        val raw = 0xA000  // opcode 0xA, address = 0x000
        val instruction = SetAInstruction(raw)

        instruction.execute(CPU, CPU.memory)

        assertEquals(0, CPU.A)
    }

    @Test
    fun `sets A to max 12-bit value 0xFFF`() {
        val raw = 0xAFFF  // opcode 0xA, address = 0xFFF
        val instruction = SetAInstruction(raw)

        instruction.execute(CPU, CPU.memory)

        assertEquals(0xFFF, CPU.A)
    }

    @Test
    fun `sets A to arbitrary middle value`() {
        val raw = 0xA456  // opcode 0xA, address = 0x456
        val instruction = SetAInstruction(raw)

        instruction.execute(CPU, CPU.memory)

        assertEquals(0x456, CPU.A)
    }

    @Test
    fun `increments program counter`() {
        val initialPC = CPU.getP()
        val instruction = SetAInstruction(0xA123)

        instruction.execute(CPU, CPU.memory)

        assertEquals(initialPC + 1, CPU.getP())
    }

    @Test
    fun `does not modify memory`() {
        val memoryCopy = CPU.memory.copyOf()
        val instruction = SetAInstruction(0xA123)

        instruction.execute(CPU, CPU.memory)

        assertContentEquals(memoryCopy, CPU.memory)
    }
}
