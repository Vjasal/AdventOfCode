package com.vjasal.aoc2019.day06;

import com.vjasal.util.AocMainClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {
    private final static Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2019, 6);
    }

    @Override
    public long solvePuzzle1(String input) {
        Map<String, SpaceObject> objectMap = parseInputToDataStructure(input);

        if (!objectMap.containsKey("COM")) {
            logger.warning("No Center of Mass (COM) found");
            return 0;
        }

        SpaceObject com = objectMap.get("COM");
        int result = com.getNumberOfOrbits(0);
        logger.info("result = " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Map<String, SpaceObject> objectMap = parseInputToDataStructure(input);

        if (!objectMap.containsKey("YOU")) {
            logger.warning("You are not on map");
            return 0;
        }

        if (!objectMap.containsKey("SAN")) {
            logger.warning("Santa is not on map");
            return 0;
        }

        SpaceObject currentObject;

        // find path from SAN to COM and mark seen objects
        List<String> pathFromSanToCom = new LinkedList<>();
        Set<String> seen = new HashSet<>();

        currentObject = objectMap.get("SAN");
        while (!currentObject.getCenterName().equals("COM")) {
            pathFromSanToCom.add(currentObject.getCenterName());
            seen.add(currentObject.getCenterName());
            currentObject = objectMap.get(currentObject.getCenterName());
        }
        pathFromSanToCom.add(currentObject.getCenterName());
        seen.add(currentObject.getCenterName());

        // now count number of steps needed to meet one of previously seen objects, while moving to COM
        int result = 0;

        currentObject = objectMap.get("YOU");
        while (!seen.contains(currentObject.getCenterName())) {
            result++;
            currentObject = objectMap.get(currentObject.getCenterName());
        }

        // finally add number of steps needed to reach santa from first common object
        String firstCommonObjectName = currentObject.getCenterName();
        Iterator<String> iterator = pathFromSanToCom.iterator();
        while (iterator.hasNext() && !iterator.next().equals(firstCommonObjectName)) {
            result++;
        }

        logger.info("result = " + result);
        return result;
    }

    private Map<String, SpaceObject> parseInputToDataStructure(String input) {
        Map<String, SpaceObject> result = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] newObjectNames = line.split("\\)", 2);
                String first  = newObjectNames[0];
                String second = newObjectNames[1];

                SpaceObject secondObject;
                if (result.containsKey(second)) {
                    secondObject = result.get(second);
                } else {
                    secondObject = new SpaceObject(second);
                    result.put(second, secondObject);
                }

                SpaceObject firstObject;
                if (result.containsKey(first)) {
                    firstObject = result.get(first);
                } else {
                    firstObject = new SpaceObject(first);
                    result.put(first, firstObject);
                }

                firstObject.addOrbitingObject(secondObject);
                secondObject.setCenterName(firstObject.getName());
            }
        } catch (IOException e) {
            logger.warning("Error while parsing input to data structure: " + e.getMessage());
        }

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