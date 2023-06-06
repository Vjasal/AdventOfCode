package com.vjasal.aoc2022.day07;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 7);
    }

    @Override
    public long solvePuzzle1(String input) {
        int limit = 100000;
        int result = parseDirectories(input).stream().mapToInt(Directory::getSize).filter(size -> size < limit).sum();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        LinkedList<Directory> directories = parseDirectories(input);
        int toDelete = 30000000 - (70000000 - directories.getFirst().getSize());
        int result = directories.stream().mapToInt(Directory::getSize).filter(size -> size >= toDelete).min().orElse(-1);
        logger.info("Result: " + result);
        return result;
    }

    public LinkedList<Directory> parseDirectories(String input) {
        LinkedList<Directory> directories = new LinkedList<>();
        Stack<Directory> pwd = new Stack<>();

        for (String[] line : CollectionUtil.toLinkedList(input).stream().map(s -> s.split(" ")).toList()) {
            if (line[0].equals("$")) {
                if (line[1].equals("cd")) {
                    // $ cd <dir_name>
                    if (line[2].equals("..")) {
                        pwd.pop();
                    } else {
                        Directory next = pwd.isEmpty() ? new Directory() : pwd.peek().getDirectory(line[2]);
                        pwd.push(next);
                        directories.add(next);
                    }
                }
            } else if (line[0].equals("dir")) {
                // dir <dir_name>
                pwd.peek().addDirectory(line[1]);
            } else {
                // <file_size> <file_name>
                pwd.peek().addFile(line[1], Integer.parseInt(line[0]));
            }
        }

        return directories;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
