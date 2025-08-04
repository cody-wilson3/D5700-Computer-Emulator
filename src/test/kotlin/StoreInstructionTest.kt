package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class StoreInstructionTest {

    @BeforeTest
    fun resetCPU() {
        for (i in 0..7) {
            CPU.setRegister(i, 0)
        }
        CPU.setP(0)
    }

    @Test
    fun `stores zero value into register`() {
        val raw = 0x0000  // registerIndex = 0, value = 0
        val instr = StoreInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(0, CPU.getRegisterVal(0).toInt() and 0xFF)
        assertEquals(1, CPU.getP())
    }

    @Test
    fun `stores max byte value 255 into register`() {
        val raw = (1 shl 8) or 0xFF  // registerIndex=1, value=255
        val instr = StoreInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(255, CPU.getRegisterVal(1).toInt() and 0xFF)
        assertEquals(1, CPU.getP())
    }

    @Test
    fun `stores arbitrary value into register`() {
        val raw = (3 shl 8) or 0x4A  // registerIndex=3, value=74 decimal
        val instr = StoreInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(74, CPU.getRegisterVal(3).toInt() and 0xFF)
        assertEquals(1, CPU.getP())
    }

    @Test
    fun `does not affect other registers`() {
        CPU.setRegister(2, 99)
        val raw = (1 shl 8) or 0x33  // registerIndex=1, value=51
        val instr = StoreInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(51, CPU.getRegisterVal(1).toInt() and 0xFF)
        assertEquals(99, CPU.getRegisterVal(2).toInt() and 0xFF)
        assertEquals(1, CPU.getP())
    }
}
