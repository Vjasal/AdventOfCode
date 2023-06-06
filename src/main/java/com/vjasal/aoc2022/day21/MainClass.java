package com.vjasal.aoc2022.day21;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 21);
    }

    @Override
    public long solvePuzzle1(String input) {
//        Pattern pattern = Pattern.compile("^(\\w+) (.) (\\w+)$");
//
//        Map<String, MonkeyJob> monkeyJobList = new HashMap<>();
//        for (String line : CollectionUtil.toLinkedList(input)) {
//            String[] in = line.split(": ");
//            Matcher matcher = pattern.matcher(in[1]);
//            if (matcher.find()) {
//                MonkeyJob.Operation op = MonkeyJob.getOp(matcher.group(2));
//                String m1 = matcher.group(1);
//                String m2 = matcher.group(3);
//                monkeyJobList.put(in[0], new MonkeyJob(m1, m2, op));
//            } else {
//                monkeyJobList.put(in[0], new MonkeyJob(Integer.parseInt(in[1])));
//            }
//        }
//
//        long result = monkeyJobList.get("root").getValue(monkeyJobList);
//
//        logger.info("" + result);
        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {
        Pattern pattern = Pattern.compile("^(\\w+) (.) (\\w+)$");

        Map<String, MonkeyJob> monkeyJobList = new HashMap<>();
        for (String line : CollectionUtil.toLinkedList(input)) {
            String[] in = line.split(": ");
            Matcher matcher = pattern.matcher(in[1]);
            if (matcher.find()) {
                MonkeyJob.Operation op = MonkeyJob.getOp(matcher.group(2));
                String m1 = matcher.group(1);
                String m2 = matcher.group(3);
                monkeyJobList.put(in[0], new MonkeyJob(in[0], m1, m2, op));
            } else {
                monkeyJobList.put(in[0], new MonkeyJob(in[0], Integer.parseInt(in[1])));
            }
        }

        long result = monkeyJobList.get("humn").test(monkeyJobList, "");
        logger.info("" + result);
        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
//        input = "root: pppw + sjmn\n" +
//                "dbpl: 5\n" +
//                "cczh: sllz + lgvd\n" +
//                "zczc: 2\n" +
//                "ptdq: humn - dvpt\n" +
//                "dvpt: 3\n" +
//                "lfqf: 4\n" +
//                "humn: 5\n" +
//                "ljgn: 2\n" +
//                "sjmn: drzm * dbpl\n" +
//                "sllz: 4\n" +
//                "pppw: cczh / lfqf\n" +
//                "lgvd: ljgn * ptdq\n" +
//                "drzm: hmdt - zczc\n" +
//                "hmdt: 32";
        logger.info("Input:\n" + input);

//        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
