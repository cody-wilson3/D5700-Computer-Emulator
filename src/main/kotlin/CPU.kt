package org.example

import org.example.Instructions.Instruction
import java.io.File

object CPU {
    private val registers = ByteArray(8)
    val rom = mutableListOf<String>()
    val memory = ByteArray(4096)
    private var P: Int = 0
    var A: Int = 0
    var T: Int = 0
    var M: Boolean = false
    val screen = Screen

    fun loadRom(filePath: String) {
        val file = File(filePath)
        file.forEachLine { line ->
            val hexNumbers = line.trim().chunked(2)
            rom.addAll(hexNumbers)
        }
    }

    fun runRom() {
        while ((P) < rom.size) {
            cycle()
        }
    }


    private fun cycle() {
//        if ((P * 2 + ) >= rom.size) return

        val hexByteOne = rom[P]
        val hexByteTwo = rom[P + 1]
        if ((hexByteOne + hexByteTwo) == "0000") {
            P = rom.size
            return
        }
        val instruction = Instruction.whichInstruction((hexByteOne + hexByteTwo).toInt(16))
        incrementCounter()
        instruction.execute(this, memory)
    }

    fun getRegisterVal(register: Int): Byte {
        return registers[register]
    }

    fun setRegister(register: Int, value: Byte) {
        registers[register] = value
    }

    fun incrementCounter() {
        P += 1
    }

    fun setP(value: Int) {
        P = value
    }


//    private fun processLine(hex: String) {
//        val decimal = hex.toInt(16)
//        val binary = decimal.toString(2).padStart(16, '0')  // For consistent 16-bit output
//
//        val firstNibble = (decimal shr 12) and 0xF
//        val secondNibble = (decimal shr 8) and 0xF
//        val thirdNibble = (decimal shr 4) and 0xF
//        val fourthNibble = decimal and 0xF
//
//        IO.println("Hex: $hex -> Binary: $binary")
//
//        IO.println("First nibble:  $firstNibble  -> Binary: ${firstNibble.toString(2).padStart(4, '0')}")
//        IO.println("Second nibble: $secondNibble -> Binary: ${secondNibble.toString(2).padStart(4, '0')}")
//        IO.println("Third nibble:  $thirdNibble  -> Binary: ${thirdNibble.toString(2).padStart(4, '0')}")
//        IO.println("Fourth nibble: $fourthNibble -> Binary: ${fourthNibble.toString(2).padStart(4, '0')}")
//    }



}