package org.example.Instructions

import org.example.CPU
import kotlin.test.*

class ConvertToBase10InstructionTest {

    @BeforeTest
    fun setup() {
        // Reset registers and memory
        for (i in 0 until 8) {
            CPU.setRegister(i, 0)
        }
        CPU.A = 0
        CPU.setP(0)
        CPU.memory.fill(0)
    }

    @Test
    fun `converts 123 correctly to memory`() {
        val value = 123
        val rX = 2
        CPU.setRegister(rX, value.toByte())
        CPU.A = 100

        val raw = (0xD shl 12) or (rX shl 8)
        val instr = ConvertToBase10Instruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(1.toByte(), CPU.memory[100])   // hundreds
        assertEquals(2.toByte(), CPU.memory[101])   // tens
        assertEquals(3.toByte(), CPU.memory[102])   // ones
    }

    @Test
    fun `converts 0 to 0 0 0`() {
        val rX = 1
        CPU.setRegister(rX, 0)
        CPU.A = 10

        val raw = (0xD shl 12) or (rX shl 8)
        val instr = ConvertToBase10Instruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(0.toByte(), CPU.memory[10])
        assertEquals(0.toByte(), CPU.memory[11])
        assertEquals(0.toByte(), CPU.memory[12])
    }

    @Test
    fun `converts 255 correctly to memory`() {
        val rX = 3
        CPU.setRegister(rX, 255.toByte())  // 255 -> 2, 5, 5
        CPU.A = 50

        val raw = (0xD shl 12) or (rX shl 8)
        val instr = ConvertToBase10Instruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(2.toByte(), CPU.memory[50])
        assertEquals(5.toByte(), CPU.memory[51])
        assertEquals(5.toByte(), CPU.memory[52])
    }

    @Test
    fun `increments program counter`() {
        CPU.setP(20)
        val rX = 0
        CPU.setRegister(rX, 99)

        val raw = (0xD shl 12) or (rX shl 8)
        val instr = ConvertToBase10Instruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(21, CPU.getP())
    }

    @Test
    fun `writes to correct memory based on A`() {
        val rX = 5
        CPU.setRegister(rX, 42)  // 042
        CPU.A = 200

        val raw = (0xD shl 12) or (rX shl 8)
        val instr = ConvertToBase10Instruction(raw)

        instr.execute(CPU, CPU.memory)

        assertEquals(0.toByte(), CPU.memory[200])
        assertEquals(4.toByte(), CPU.memory[201])
        assertEquals(2.toByte(), CPU.memory[202])
    }
}
