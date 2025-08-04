package org.example

import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScreenTest {

    private val outContent = ByteArrayOutputStream()
    private val originalOut = System.out

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outContent))

        // Reset screen before each test by drawing dashes over everything
        for (row in 0 until 8) {
            for (col in 0 until 8) {
                Screen.drawChar(row, col, '-')
            }
        }

        outContent.reset()
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun `drawChar should place character within bounds`() {
        Screen.drawChar(2, 3, 'X')
        Screen.render()
        val output = outContent.toString().split("\n")[2]
        assertEquals("---X----", output.trim())
    }

    @Test
    fun `drawChar should not draw character outside bounds`() {
        Screen.drawChar(-1, 0, 'X')
        Screen.drawChar(0, -1, 'X')
        Screen.drawChar(8, 0, 'X')
        Screen.drawChar(0, 8, 'X')

        Screen.render()
        val lines = outContent.toString().lines().filter { it.isNotBlank() }
        for (line in lines) {
            assertEquals("--------", line.trim())
        }
    }

    @Test
    fun `render should output 8 rows and 8 columns`() {
        Screen.render()
        val lines = outContent.toString().lines().filter { it.isNotBlank() }
        assertEquals(8, lines.size)
        for (line in lines) {
            assertEquals(8, line.trim().length)
        }
    }

    @Test
    fun `drawChar should overwrite existing characters`() {
        Screen.drawChar(4, 4, 'A')
        Screen.drawChar(4, 4, 'B')
        Screen.render()
        val output = outContent.toString().split("\n")[4]
        assertEquals("----B---", output.trim())
    }

    @Test
    fun `initial screen state should be blank`() {
        Screen.render()
        val lines = outContent.toString().lines().filter { it.isNotBlank() }
        for (line in lines) {
            assertEquals("--------", line.trim())
        }
    }


}
