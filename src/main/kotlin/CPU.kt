package org.example

import org.example.Instructions.Instruction
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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
            val hexNumbers = line.trim().substring(0,4).chunked(2)
            rom.addAll(hexNumbers)
        }
    }

    fun runRom() {
        val executor = Executors.newSingleThreadScheduledExecutor()

        val runnable = Runnable {
            CPU.cycle()
        }

        val cpuFuture = executor.scheduleAtFixedRate(
            runnable,
            0,
            1000L / 500L,
            TimeUnit.MILLISECONDS)

        // to wait for all futures to finish
        try {
            cpuFuture.get() // waits for future to finish or be cancelled - blocks current thread execution (repeating futures will still run)
        } catch (_: Exception) {
            executor.shutdown() // turns off the executor allowing the program to terminate when the end is reached
        }
    }


    private fun cycle() {

        val hexByteOne = rom[P]
        val hexByteTwo = rom[P + 1]
        if ((hexByteOne + hexByteTwo) == "0000") {
            P = rom.size
            return
        }
        val instruction = Instruction.whichInstruction((hexByteOne + hexByteTwo).toInt(16))
        incrementCounter()
        instruction.execute(this, memory)

        if (CPU.T > 0) {CPU.T--}
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

    fun getP(): Int {
        return P
    }
}