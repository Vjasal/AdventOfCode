package com.vjasal.aoc2020.day04;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Passport {

    private final Map<String, String> fields = new HashMap<>();
    private final List<String> requiredFields = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    void addField(String key, String value) {
        fields.put(key, value);
    }

    boolean isValid1() {
        return fields.keySet().containsAll(requiredFields);
    }

    boolean isValid2() {
        if (!fields.keySet().containsAll(requiredFields))
            return false;

        // byr (Birth Year) - four digits; at least 1920 and at most 2002
        if (!fields.get("byr").matches("^(19[2-9][0-9]|200[0-2])$"))
            return false;

        // iyr (Issue Year) - four digits; at least 2010 and at most 2020
        if (!fields.get("iyr").matches("^(201[0-9]|2020)$"))
            return false;

        // eyr (Expiration Year) - four digits; at least 2020 and at most 2030
        if (!fields.get("eyr").matches("^(202[0-9]|2030)$"))
            return false;

        // hgt (Height) - a number followed by either cm or in:
        //    If cm, the number must be at least 150 and at most 193
        //    If in, the number must be at least 59 and at most 76
        if (!fields.get("hgt").matches("^((1[5-8][0-9]|19[0-3])cm|(59|6[0-9]|7[0-6])in)$"))
            return false;

        // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f
        if (!fields.get("hcl").matches("^#[0-9a-f]{6}$"))
            return false;

        // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth
        if (!fields.get("ecl").matches("^(amb|blu|brn|gry|grn|hzl|oth)$"))
            return false;

        // pid (Passport ID) - a nine-digit number, including leading zeroes
        return fields.get("pid").matches("^[0-9]{9}$");
    }
}
