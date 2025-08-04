package org.example.Instructions

import org.example.CPU

abstract class Instruction(private val raw: Int) {

    fun run(cpu: CPU, memory: ByteArray) {
        decode()
        execute(cpu, memory)
    }

    protected abstract fun decode()
    abstract fun execute(cpu: CPU, memory: ByteArray)



    companion object {
        fun whichInstruction(raw: Int): Instruction {
            val opcode = (raw shr 12) and 0xF
            return when (opcode) {
                0x0 -> StoreInstruction(raw)
                0x1 -> AddInstruction(raw)
                0x2 -> SubtractInstruction(raw)
                0x3 -> ReadInstruction(raw)
                0x4 -> WriteInstruction(raw)
                0x5 -> JumpInstruction(raw)
                0x6 -> ReadKeyboardInstruction(raw)
                0x7 -> SwitchMemoryInstruction(raw)
                0x8 -> SkipEqualInstruction(raw)
                0x9 -> SkipNotEqualInstruction(raw)
                0xA -> SetAInstruction(raw)
                0xB -> SetTInstruction(raw)
                0xC -> ReadTInstruction(raw)
                0xD -> ConvertToBase10Instruction(raw)
                0xE -> ConvertByteToAsciiInstruction(raw)
                0xF -> DrawInstruction(raw)
                else -> throw IllegalArgumentException("Unknown opcode: $opcode")
            }
        }
    }
}