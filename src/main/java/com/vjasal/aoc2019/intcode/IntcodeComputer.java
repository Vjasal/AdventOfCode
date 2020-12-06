package com.vjasal.aoc2019.intcode;

import java.util.StringJoiner;
import java.util.logging.Logger;

public class IntcodeComputer {

    private static final Logger logger = Logger.getLogger(IntcodeComputer.class.getName());

    private final IntcodeMemory memory;

    public IntcodeComputer(String program) {
        memory = new IntcodeMemory(program);
    }

    public void writeMemory(int address, int value) {
        memory.set(address, value);
    }

    public int readMemory(int address) {
        return memory.get(address);
    }

    public void solve() throws IllegalOpcodeException {
        int pc = 0;
        while (true) {
            // logInstruction(pc);
            int opcode = memory.get(pc);
            switch (opcode) {
                case 1:
                    pc = add(pc);
                    break;
                case 2:
                    pc = multiply(pc);
                    break;
                case 99:
                    return;
                default:
                    throw new IllegalOpcodeException();
            }
        }
    }

    private int add(int pc) {
        int val1 = memory.get(memory.get(pc + 1));
        int val2 = memory.get(memory.get(pc + 2));
        int addr = memory.get(pc + 3);
        memory.set(addr, val1 + val2);
        return pc + 4;
    }

    private int multiply(int pc) {
        int val1 = memory.get(memory.get(pc + 1));
        int val2 = memory.get(memory.get(pc + 2));
        int addr = memory.get(pc + 3);
        memory.set(addr, val1 * val2);
        return pc + 4;
    }

    private void logInstruction(int pc) {
        int opcode = memory.get(pc);
        switch (opcode) {
            case 1:
                logger.info(getInstructionParamString(pc, 4) + " - add");
                break;
            case 2:
                logger.info(getInstructionParamString(pc, 4) + " - multiply");
                break;
            case 99:
                logger.info(getInstructionParamString(pc, 1) + " - end");
                break;
            default:
                logger.info("Illegal opcode: " + opcode);
                break;
        }
    }

    private String getInstructionParamString(int pc, int instructionSize) {
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < instructionSize; i++) {
            joiner.add(String.valueOf(memory.get(pc + i)));
        }
        return joiner.toString();
    }

    @Override
    public String toString() {
        return memory.toString();
    }
}
