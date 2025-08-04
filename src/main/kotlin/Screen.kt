package org.example

object Screen {
    private const val width = 8
    private const val height = 8

    // 2D Array representing the screen buffer (initialized with spaces)
    private val buffer = Array(height) { CharArray(width) { '-' } }

    // Write a character to a specific row and column
    fun drawChar(row: Int, col: Int, char: Char) {
        if (row in 0 until height && col in 0 until width) {
            buffer[row][col] = char
        }

    }

    // Render the screen buffer to the console
    fun render() {
        for (row in buffer) {
            println(row.concatToString())
        }
    }


}
