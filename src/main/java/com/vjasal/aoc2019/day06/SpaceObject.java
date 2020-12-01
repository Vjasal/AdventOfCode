package com.vjasal.aoc2019.day06;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

class SpaceObject {
    private static final Logger logger = Logger.getLogger(SpaceObject.class.getName());

    private final List<SpaceObject> orbitingObjects = new LinkedList<>();
    private final String name;
    private String centerName;

    SpaceObject(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void setCenterName(String name) {
        this.centerName = name;
    }

    String getCenterName() {
        return centerName;
    }

    void addOrbitingObject(SpaceObject object) {
        orbitingObjects.add(object);
    }

    int getNumberOfOrbits(int objectValue) {
        int result = objectValue;
        for (SpaceObject object : orbitingObjects) {
            result += object.getNumberOfOrbits(objectValue + 1);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(" { ");
        for (SpaceObject object : orbitingObjects) {
            builder.append(object.name);
            builder.append(" ");
        }
        builder.append("}");
        return builder.toString();
    }
}
