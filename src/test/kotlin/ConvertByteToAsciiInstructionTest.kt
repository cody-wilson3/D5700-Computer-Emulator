package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class ConvertByteToAsciiInstructionTest {

    @BeforeTest
    fun resetCPU() {
        for (i in 0 until 8) {
            CPU.setRegister(i, 0)
        }
        CPU.setP(0)
    }

    @Test
    fun `converts 0 to ASCII '0'`() {
        CPU.setRegister(1, 0)  // rX = 1, value = 0
        val raw = (0xE shl 12) or (1 shl 8) or (2 shl 4)  // rY = 2

        val instr = ConvertByteToAsciiInstruction(raw)
        instr.execute(CPU, ByteArray(4096))

        assertEquals('0'.code.toByte(), CPU.getRegisterVal(2))
    }

    @Test
    fun `converts 9 to ASCII '9'`() {
        CPU.setRegister(0, 9)
        val raw = (0xE shl 12) or (0 shl 8) or (3 shl 4)

        val instr = ConvertByteToAsciiInstruction(raw)
        instr.execute(CPU, ByteArray(4096))

        assertEquals('9'.code.toByte(), CPU.getRegisterVal(3))
    }

    @Test
    fun `converts 10 to ASCII 'A'`() {
        CPU.setRegister(2, 10)
        val raw = (0xE shl 12) or (2 shl 8) or (4 shl 4)

        val instr = ConvertByteToAsciiInstruction(raw)
        instr.execute(CPU, ByteArray(4096))

        assertEquals('A'.code.toByte(), CPU.getRegisterVal(4))
    }

    @Test
    fun `converts 15 to ASCII 'F'`() {
        CPU.setRegister(7, 15)
        val raw = (0xE shl 12) or (7 shl 8) or (6 shl 4)

        val instr = ConvertByteToAsciiInstruction(raw)
        instr.execute(CPU, ByteArray(4096))

        assertEquals('F'.code.toByte(), CPU.getRegisterVal(6))
    }

    @Test
    fun `throws error if value is greater than 0xF`() {
        CPU.setRegister(3, 0x1F)  // 31

        val raw = (0xE shl 12) or (3 shl 8) or (4 shl 4)
        val instr = ConvertByteToAsciiInstruction(raw)

        val exception = assertFailsWith<IllegalStateException> {
            instr.execute(CPU, ByteArray(4096))
        }

        assertTrue(exception.message!!.contains("exceeds 0xF"))
    }

    @Test
    fun `increments program counter`() {
        CPU.setP(12)
        CPU.setRegister(1, 0xA) // Valid input

        val raw = (0xE shl 12) or (1 shl 8) or (2 shl 4)
        val instr = ConvertByteToAsciiInstruction(raw)

        instr.execute(CPU, ByteArray(4096))

        assertEquals(13, CPU.getP())
    }
}
