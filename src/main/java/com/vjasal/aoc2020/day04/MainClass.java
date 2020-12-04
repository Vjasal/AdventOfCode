package com.vjasal.aoc2020.day04;

import com.vjasal.util.AocMainClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2020, 4);
    }

    @Override
    public void solvePuzzle1(String input) {
        List<Passport> passports = parseInput(input);
        long result = passports.stream().filter(Passport::isValid1).count();
        logger.info("Result: " + result);
    }

    @Override
    public void solvePuzzle2(String input) {
        List<Passport> passports = parseInput(input);
        long result = passports.stream().filter(Passport::isValid2).count();
        logger.info("Result: " + result);
    }

    private List<Passport> parseInput(String input) {
        List<Passport> passports = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(groupLines(input)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Passport passport = new Passport();
                for (String var : line.split(" ")) {
                    String[] a = var.split(":");
                    passport.addField(a[0], a[1]);
                }
                passports.add(passport);
            }
        } catch (IOException e) {
            logger.warning("Exception: " + e);
        }
        return passports;
    }

    private String groupLines(String input) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    builder.append("\n");
                } else {
                    builder.append(line).append(" ");
                }
            }
        } catch (IOException e) {
            logger.warning("Exception: " + e);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
