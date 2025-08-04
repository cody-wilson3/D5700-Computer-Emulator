package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class SubtractInstructionTest {

    @BeforeTest
    fun resetCPU() {
        for (i in 0..7) {
            CPU.setRegister(i, 0)
        }
        CPU.setP(0)
    }

    @Test
    fun `subtracts smaller from larger and stores result`() {
        CPU.setRegister(1, 100)
        CPU.setRegister(2, 30)
        val raw = (0x2 shl 12) or (1 shl 8) or (2 shl 4) or 3 // opcode=2, rX=1, rY=2, rZ=3
        val instr = SubtractInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(70, CPU.getRegisterVal(3).toInt() and 0xFF)
        assertEquals(1, CPU.getP())
    }

    @Test
    fun `subtracts equal values results in zero`() {
        CPU.setRegister(0, 50)
        CPU.setRegister(4, 50)
        val raw = (0x2 shl 12) or (0 shl 8) or (4 shl 4) or 5 // rX=0, rY=4, rZ=5
        val instr = SubtractInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(0, CPU.getRegisterVal(5).toInt() and 0xFF)
        assertEquals(1, CPU.getP())
    }

    @Test
    fun `subtracts larger from smaller results in wraparound`() {
        CPU.setRegister(6, 20)
        CPU.setRegister(7, 50)
        val raw = (0x2 shl 12) or (6 shl 8) or (7 shl 4) or 0 // rX=6, rY=7, rZ=0
        val instr = SubtractInstruction(raw)

        instr.execute(CPU, CPU.memory)

        // 20 - 50 = -30 mod 256 = 226
        assertEquals(226, CPU.getRegisterVal(0).toInt() and 0xFF)
        assertEquals(1, CPU.getP())
    }
}
