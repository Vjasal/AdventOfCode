package com.vjasal.aoc2022.day13;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 13);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);

        int result = 0;
        for (int i = 0; i < sections.size(); i++) {
            String[] lines = sections.get(i).split("\n");
            Packet p1 = Packet.parse(lines[0]);
            Packet p2 = Packet.parse(lines[1]);
            if (Packet.compare(p1, p2) < 0) result += i + 1;
        }

        logger.info("Result: " + result);
        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<Packet> packets = CollectionUtil.toArrayList(input.replace("\n\n", "\n")).stream()
                .map(Packet::parse).collect(Collectors.toList());

        Packet p1 = Packet.parse("[[2]]");
        Packet p2 = Packet.parse("[[6]]");
        packets.add(p1);
        packets.add(p2);
        packets.sort(Packet::compare);

        int result = (packets.indexOf(p1) + 1) * (packets.indexOf(p2) + 1);
        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
