package com.vjasal.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public abstract class AocMainClass {

    private final static Logger logger = Logger.getLogger(AocMainClass.class.getName());

    private final static String SESSION_TOKEN_FILE = "session_token.txt";
    private final static String INPUT_NAME_FORMAT = "input_%d_%02d.txt";
    private final static String INPUTS_DIR = "inputs";
    private final static String HOST = "https://adventofcode.com";

    private final int year;
    private final int day;

    public AocMainClass(int year, int day) {
        this.year = year;
        this.day  = day;
    }

    public abstract void solvePuzzle1(String input);
    public abstract void solvePuzzle2(String input);

    public String getInput() {
        String input = readInputFile();
        if (input != null) {
            logger.info("Input was red from resource file");
            return input;
        }

        String token = readSessionToken();
        if (token == null) {
            logger.warning("No input file found and no session token");
            return null;
        }

        try {
            URL url = new URL(String.format("%s/%d/day/%d/input", HOST, year, day));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("cookie", "session=" + token);

            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
            }

            input = stringBuilder.toString();
            writeInputFile(input);
            return input;
        } catch (IOException e) {
            logger.info("Error while getting input from url: " + e.getMessage());
            return null;
        }
    }

    private String readInputFile() {
        String path = String.format(INPUTS_DIR + "/" + INPUT_NAME_FORMAT, year, day);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            logger.warning("Input file (" + path + ") not found");
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            logger.warning("Error while reading input file (" + path + ")");
            return null;
        }
    }

    private void writeInputFile(String input) {
        File file = new File(String.format(INPUT_NAME_FORMAT, year, day));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(input);
        } catch (IOException e) {
            logger.warning("Error while writing input to file: " + e.getMessage());
        }
    }

    private String readSessionToken() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SESSION_TOKEN_FILE);
        if (inputStream == null) {
            logger.warning("Session token file not found");
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.readLine();
        } catch (IOException e) {
            logger.warning("Error reading session token file");
            return null;
        }
    }
}