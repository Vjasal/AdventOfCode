package com.vjasal.aoc2022.day07;

import java.util.HashMap;
import java.util.Map;

public class Directory {

    private final Map<String, Directory> directories = new HashMap<>();
    private final Map<String, Integer> files = new HashMap<>();

    public void addDirectory(String name) {
        directories.put(name, new Directory());
    }

    public void addFile(String name, int size) {
        files.put(name, size);
    }

    public int getSize() {
        int fileSizeSum = files.values().stream().reduce(0, Integer::sum);
        int dirsSizeSum = directories.values().stream().map(Directory::getSize).reduce(0, Integer::sum);
        return fileSizeSum + dirsSizeSum;
    }

    public Directory getDirectory(String name) {
        return directories.get(name);
    }

    @Override
    public String toString() {
        return "Directory{" +
                "directories=" + directories +
                ", files=" + files +
                '}';
    }
}
