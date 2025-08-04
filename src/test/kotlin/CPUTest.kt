package org.example

import org.example.Instructions.Instruction
import org.junit.jupiter.api.*
import java.io.File
import java.nio.file.Files
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertContentEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CPUTest {
    private val testRomPath = "test.rom"

    @BeforeEach
    fun setup() {
        CPU.setP(0)
        for (i in 0..7) CPU.setRegister(i, 0)
        CPU.rom.clear()
        CPU.T = 0
        CPU.A = 0
        CPU.M = false
        CPU.memory.fill(0)
        File(testRomPath).delete()
    }

    @Test
    fun `loadRom loads hex instructions correctly`() {
        val romLines = listOf("A1B2", "C3D4", "0000")
        File(testRomPath).writeText(romLines.joinToString("\n"))
        CPU.loadRom(testRomPath)

        assertEquals(listOf("A1", "B2", "C3", "D4", "00", "00"), CPU.rom)
    }

    @Test
    fun `getRegisterVal and setRegister should persist register values`() {
        CPU.setRegister(3, 0x42.toByte())
        assertEquals(0x42.toByte(), CPU.getRegisterVal(3))
    }

    @Test
    fun `incrementCounter should increase program counter`() {
        CPU.setP(2)
        CPU.incrementCounter()
        assertEquals(3, getPrivateField("P"))
    }

    @Test
    fun `cycle should exit on 0000 instruction`() {
        CPU.rom.clear()
        CPU.rom.addAll(listOf("00", "00"))
        callPrivateCycle()
        assertEquals(CPU.rom.size, getPrivateField("P"))
    }

    @Test
    fun `memory should be initialized with 4096 bytes`() {
        assertEquals(4096, CPU.memory.size)
    }

    private fun getPrivateField(field: String): Int {
        val pField = CPU::class.java.getDeclaredField(field)
        pField.isAccessible = true
        return pField.get(CPU) as Int
    }

    private fun callPrivateCycle() {
        val cycleMethod = CPU::class.java.getDeclaredMethod("cycle")
        cycleMethod.isAccessible = true
        cycleMethod.invoke(CPU)
    }

    @Test
    fun `setRegister should fail when register index is out of bounds`() {
        assertFailsWith<ArrayIndexOutOfBoundsException> {
            CPU.setRegister(10, 0x33.toByte())
        }
    }

    @Test
    fun `getRegisterVal should fail when register index is out of bounds`() {
        assertFailsWith<ArrayIndexOutOfBoundsException> {
            CPU.getRegisterVal(-1)
        }
    }

    @Test
    fun `loadRom should skip or throw on short hex line`() {
        File(testRomPath).writeText("A1\nC3D4")
        assertFailsWith<StringIndexOutOfBoundsException> {
            CPU.loadRom(testRomPath)
        }
    }
}
