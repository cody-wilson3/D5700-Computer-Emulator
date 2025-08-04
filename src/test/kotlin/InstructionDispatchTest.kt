package org.example.Instructions

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class InstructionDispatchTest {

    @Test
    fun `opcode 0 should return StoreInstruction`() {
        val instr = Instruction.whichInstruction(0x0123)
        assertTrue(instr is StoreInstruction)
    }

    @Test
    fun `opcode 1 should return AddInstruction`() {
        val instr = Instruction.whichInstruction(0x1123)
        assertTrue(instr is AddInstruction)
    }

    @Test
    fun `opcode 2 should return SubtractInstruction`() {
        val instr = Instruction.whichInstruction(0x2123)
        assertTrue(instr is SubtractInstruction)
    }

    @Test
    fun `opcode 3 should return ReadInstruction`() {
        val instr = Instruction.whichInstruction(0x3123)
        assertTrue(instr is ReadInstruction)
    }

    @Test
    fun `opcode 4 should return WriteInstruction`() {
        val instr = Instruction.whichInstruction(0x4123)
        assertTrue(instr is WriteInstruction)
    }

    @Test
    fun `opcode 5 should return JumpInstruction`() {
        val instr = Instruction.whichInstruction(0x5123)
        assertTrue(instr is JumpInstruction)
    }

    @Test
    fun `opcode 6 should return ReadKeyboardInstruction`() {
        val instr = Instruction.whichInstruction(0x6123)
        assertTrue(instr is ReadKeyboardInstruction)
    }

    @Test
    fun `opcode 7 should return SwitchMemoryInstruction`() {
        val instr = Instruction.whichInstruction(0x7123)
        assertTrue(instr is SwitchMemoryInstruction)
    }

    @Test
    fun `opcode 8 should return SkipEqualInstruction`() {
        val instr = Instruction.whichInstruction(0x8123)
        assertTrue(instr is SkipEqualInstruction)
    }

    @Test
    fun `opcode 9 should return SkipNotEqualInstruction`() {
        val instr = Instruction.whichInstruction(0x9123)
        assertTrue(instr is SkipNotEqualInstruction)
    }

    @Test
    fun `opcode A should return SetAInstruction`() {
        val instr = Instruction.whichInstruction(0xA123)
        assertTrue(instr is SetAInstruction)
    }

    @Test
    fun `opcode B should return SetTInstruction`() {
        val instr = Instruction.whichInstruction(0xB123)
        assertTrue(instr is SetTInstruction)
    }

    @Test
    fun `opcode C should return ReadTInstruction`() {
        val instr = Instruction.whichInstruction(0xC123)
        assertTrue(instr is ReadTInstruction)
    }

    @Test
    fun `opcode D should return ConvertToBase10Instruction`() {
        val instr = Instruction.whichInstruction(0xD123)
        assertTrue(instr is ConvertToBase10Instruction)
    }

    @Test
    fun `opcode E should return ConvertByteToAsciiInstruction`() {
        val instr = Instruction.whichInstruction(0xE123)
        assertTrue(instr is ConvertByteToAsciiInstruction)
    }

    @Test
    fun `opcode F should return DrawInstruction`() {
        val instr = Instruction.whichInstruction(0xF123)
        assertTrue(instr is DrawInstruction)
    }
}
