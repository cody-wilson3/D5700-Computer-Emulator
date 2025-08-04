package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class AddInstructionTest {

    @BeforeTest
    fun setup() {
        // Reset all registers and memory before each test
        for (i in 0 until 8) {
            CPU.setRegister(i, 0)
        }
        CPU.setP(0)
    }

    @Test
    fun `add two positive values`() {
        val rX = 1
        val rY = 2
        val rZ = 3

        CPU.setRegister(rX, 10)
        CPU.setRegister(rY, 20)

        // opcode 0x1, rX = 1, rY = 2, rZ = 3
        val raw = (0x1 shl 12) or (rX shl 8) or (rY shl 4) or rZ
        val instr = AddInstruction(raw)

        instr.decode()
        instr.execute(CPU, ByteArray(4096))

        assertEquals(30.toByte(), CPU.getRegisterVal(rZ))
    }

    @Test
    fun `addition result wraps around at 255`() {
        val rX = 1
        val rY = 2
        val rZ = 0

        CPU.setRegister(rX, 200.toByte())
        CPU.setRegister(rY, 100.toByte())

        val raw = (0x1 shl 12) or (rX shl 8) or (rY shl 4) or rZ
        val instr = AddInstruction(raw)

        instr.decode()
        instr.execute(CPU, ByteArray(4096))

        val expected = ((200 + 100) and 0xFF).toByte() // 300 & 0xFF = 44
        assertEquals(expected, CPU.getRegisterVal(rZ))
    }

    @Test
    fun `add with negative values stored in bytes`() {
        val rX = 1
        val rY = 2
        val rZ = 3

        CPU.setRegister(rX, (-10).toByte()) // 0xF6
        CPU.setRegister(rY, 20.toByte())

        val raw = (0x1 shl 12) or (rX shl 8) or (rY shl 4) or rZ
        val instr = AddInstruction(raw)

        instr.decode()
        instr.execute(CPU, ByteArray(4096))

        val xVal = CPU.getRegisterVal(rX).toInt() and 0xFF // 246
        val yVal = CPU.getRegisterVal(rY).toInt() and 0xFF // 20
        val expected = ((xVal + yVal) and 0xFF).toByte()   // 266 & 0xFF = 10

        assertEquals(expected, CPU.getRegisterVal(rZ))
    }

    @Test
    fun `counter increments after execution`() {
        CPU.setP(5)

        val rX = 0
        val rY = 0
        val rZ = 0

        val raw = (0x1 shl 12) or (rX shl 8) or (rY shl 4) or rZ
        val instr = AddInstruction(raw)

        instr.decode()
        instr.execute(CPU, ByteArray(4096))

        assertEquals(6, CPU.getP())
    }
}
