package org.example
import java.io.File
import java.io.IO.println



fun main() {
    print("Enter path to program (.d5700 file): ")
    val filePath = "src/main/roms/" + readlnOrNull()?.trim()
//    val filePath = "src/main/roms/timer.d5700"

    if (filePath.isNullOrEmpty()) {
        println("No file path provided. Exiting.")
        return
    }

    val D5700 = CPU

    try {
        D5700.loadRom(filePath)
        println("Program loaded successfully.")
        D5700.runRom()
    } catch (e: Exception) {
        println("Error loading or running program: ${e.message}")
    }
}
