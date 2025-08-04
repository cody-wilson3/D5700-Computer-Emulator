package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class DrawInstructionTest {

    @BeforeTest
    fun setup() {
        for (i in 0..7) {
            CPU.setRegister(i, 0)
        }
        CPU.setP(0)
        CPU.memory.fill(0)
    }

    @Test
    fun `draws character at correct screen position`() {
        val asciiChar = 'A'  // 0x41
        val rX = 1
        val rY = 2
        val rZ = 3

        CPU.setRegister(rX, asciiChar.code.toByte())
        val raw = (0xF shl 12) or (rX shl 8) or (rY shl 4) or rZ

        val draw = DrawInstruction(raw)
        draw.execute(CPU, CPU.memory)

        val expectedAddr = rY * 8 + rZ
        assertEquals(asciiChar.code.toByte(), CPU.memory[expectedAddr])
    }

    @Test
    fun `throws error if ascii greater than 0x7F`() {
        val rX = 0
        val rY = 1
        val rZ = 2

        CPU.setRegister(rX, 0x80.toByte())  // Invalid ASCII > 0x7F
        val raw = (0xF shl 12) or (rX shl 8) or (rY shl 4) or rZ

        val draw = DrawInstruction(raw)

        val exception = assertFailsWith<IllegalStateException> {
            draw.execute(CPU, CPU.memory)
        }

        assertTrue("ASCII value" in exception.message.orEmpty())
    }

    @Test
    fun `throws error for out of bounds row`() {
        val rX = 0
        val rY = 8
        val rZ = 3

        CPU.setRegister(rX, 'B'.code.toByte())
        val raw = (0xF shl 12) or (rX shl 8) or (rY shl 4) or rZ

        val draw = DrawInstruction(raw)

        val exception = assertFailsWith<IllegalStateException> {
            draw.execute(CPU, CPU.memory)
        }

        assertTrue("Row" in exception.message.orEmpty())
    }

    @Test
    fun `throws error for out of bounds column`() {
        val rX = 0
        val rY = 2
        val rZ = 10

        CPU.setRegister(rX, 'C'.code.toByte())
        val raw = (0xF shl 12) or (rX shl 8) or (rY shl 4) or rZ

        val draw = DrawInstruction(raw)

        val exception = assertFailsWith<IllegalStateException> {
            draw.execute(CPU, CPU.memory)
        }

        assertTrue("column" in exception.message.orEmpty())
    }

    @Test
    fun `increments program counter`() {
        val rX = 1
        val rY = 2
        val rZ = 3

        CPU.setRegister(rX, '*'.code.toByte())
        CPU.setP(100)

        val raw = (0xF shl 12) or (rX shl 8) or (rY shl 4) or rZ
        val draw = DrawInstruction(raw)

        draw.execute(CPU, CPU.memory)

        assertEquals(101, CPU.getP())
    }
}
