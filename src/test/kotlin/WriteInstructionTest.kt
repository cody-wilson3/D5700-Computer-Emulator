package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class WriteInstructionTest {

    @BeforeTest
    fun resetCPU() {
        CPU.setP(0)
        CPU.A = 0
        CPU.M = false
        CPU.rom.clear()
        // Initialize ROM with 256 dummy values for testing
        repeat(256) { CPU.rom.add("00") }
        CPU.memory.fill(0)
        for (i in 0..7) {
            CPU.setRegister(i, 0)
        }
    }

    @Test
    fun `writes register value to RAM when M is false and address in bounds`() {
        CPU.M = false
        CPU.A = 10
        CPU.setRegister(3, 0xAB.toByte())

        val raw = (0x4 shl 12) or (3 shl 8) // opcode 4, rX=3
        val instr = WriteInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(0xAB.toByte(), CPU.memory[10])
        assertEquals(1, CPU.getP())
    }

    @Test
    fun `does not write to RAM if address out of bounds`() {
        CPU.M = false
        CPU.A = CPU.memory.size + 1
        CPU.setRegister(1, 0x55.toByte())

        val raw = (0x4 shl 12) or (1 shl 8)
        val instr = WriteInstruction(raw)

        instr.execute(CPU, CPU.memory)

        // Memory unchanged at out of bounds index, no exception
        assertEquals(0, CPU.memory.getOrNull(CPU.A) ?: 0)
        assertEquals(1, CPU.getP())
    }

    @Test
    fun `writes register value to ROM when M is true and address in bounds`() {
        CPU.M = true
        CPU.A = 5
        CPU.setRegister(2, 0x3C.toByte())

        // ROM initially "00" at index 5
        assertEquals("00", CPU.rom[5])

        val raw = (0x4 shl 12) or (2 shl 8)
        val instr = WriteInstruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals("60", CPU.rom[5]) // 0x3C = 60 decimal string stored
        assertEquals(1, CPU.getP())
    }

    @Test
    fun `does not write to ROM if address out of bounds`() {
        CPU.M = true
        CPU.A = CPU.rom.size + 10
        CPU.setRegister(0, 0x11.toByte())

        val raw = (0x4 shl 12) or (0 shl 8)
        val instr = WriteInstruction(raw)

        instr.execute(CPU, CPU.memory)

        // ROM unchanged
        assertTrue(CPU.rom.none { it == "17" }) // 0x11 decimal is 17
        assertEquals(1, CPU.getP())
    }
}
