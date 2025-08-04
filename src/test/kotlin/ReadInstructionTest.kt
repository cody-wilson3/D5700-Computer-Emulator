package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class ReadInstructionTest {

    @BeforeTest
    fun setup() {
        CPU.setP(0)
        CPU.A = 0
        CPU.M = false
        CPU.rom.clear()
        CPU.memory.fill(0)
        CPU.setRegister(2, 0)
    }


    @Test
    fun `reads from ROM when M is true`() {
        CPU.rom.addAll(listOf("00", "FF", "1A")) // index 2 has hex 0x1A = 26
        CPU.A = 2
        CPU.M = true

        val raw = (0x3 shl 12) or (0x2 shl 8)
        val instr = ReadInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(26.toByte(), CPU.getRegisterVal(2))
    }


}
