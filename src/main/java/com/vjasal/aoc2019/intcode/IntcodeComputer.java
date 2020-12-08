package com.vjasal.aoc2019.intcode;

import java.io.IOException;
import java.util.StringJoiner;
import java.util.logging.Logger;

public class IntcodeComputer {

    private static final Logger logger = Logger.getLogger(IntcodeComputer.class.getName());

    private final IntcodeMemory memory;
    private int programCounter = 0;

    private Integer inputRegister = null;
    private Integer outputRegister = null;

    public IntcodeComputer(String program) {
        memory = new IntcodeMemory(program);
    }

    public void writeMemory(int address, int value) {
        memory.set(address, value);
    }

    public int readMemory(int address) {
        return memory.get(address);
    }

    public void writeInput(int value) throws IOException {
        if (inputRegister != null)
            throw new IOException();
        inputRegister = value;
    }

    public int readOutput() throws IOException {
        if (outputRegister == null)
            throw new IOException();
        int value = outputRegister;
        outputRegister = null;
        return value;
    }

    private void writeOutput(int value) throws IOException {
        if (outputRegister != null)
            throw new IOException();
        outputRegister = value;
    }

    private int readInput() throws IOException {
        if (inputRegister == null)
            throw new IOException();
        int value = inputRegister;
        inputRegister = null;
        return value;
    }

    public boolean solve() throws IllegalOpcodeException, IOException {
        while (true) {
            logInstruction(programCounter);
            int opcode = memory.get(programCounter);
            switch (opcode % 100) {
                case 1:
                    programCounter = add(programCounter);
                    break;
                case 2:
                    programCounter = multiply(programCounter);
                    break;
                case 3:
                    programCounter = input(programCounter);
                    break;
                case 4:
                    programCounter = output(programCounter);
                    return true;
                case 99:
                    return false;
                default:
                    throw new IllegalOpcodeException();
            }
        }
    }

    private int add(int pc) throws IllegalOpcodeException {
        int param1  = getParam(pc, 1);
        int param2  = getParam(pc, 2);
        int address = memory.get(pc + 3);
        memory.set(address, param1 + param2);
        return pc + 4;
    }

    private int multiply(int pc) throws IllegalOpcodeException {
        int param1  = getParam(pc, 1);
        int param2  = getParam(pc, 2);
        int address = memory.get(pc + 3);
        memory.set(address, param1 * param2);
        return pc + 4;
    }

    private int input(int pc) throws IOException {
        int address = memory.get(pc + 1);
        memory.set(address, readInput());
        return pc + 2;
    }

    private int output(int pc) throws IOException {
        int value = memory.get(memory.get(pc + 1));
        writeOutput(value);
        return pc + 2;
    }

    private int getParam(int pc, int index) throws IllegalOpcodeException {
        int mode = ((memory.get(pc) / 100) / (int)Math.pow(10, index - 1)) % 10;
        if (mode == 0)
            return memory.get(memory.get(pc + index));
        else if (mode == 1)
            return memory.get(pc + index);
        else
            throw new IllegalOpcodeException();
    }

    private void logInstruction(int pc) {
        int opcode = memory.get(pc);
        switch (opcode % 100) {
            case 1:
                logger.info(getInstructionParamString(pc, 4) + " - add");
                break;
            case 2:
                logger.info(getInstructionParamString(pc, 4) + " - multiply");
                break;
            case 3:
                logger.info(getInstructionParamString(pc, 2) + " - input");
                break;
            case 4:
                logger.info(getInstructionParamString(pc, 2) + " - output");
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
