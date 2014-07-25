package net.slintes.webracer.web.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * converts our own track format (simple string) to quintus format (json array of arrays)
 */
public class TrackConverter {

    public String convertToJson(String track) {

        // create array of arrays
        StringBuilder json = new StringBuilder();
        json.append("[\n");

        BufferedReader reader = new BufferedReader(new StringReader(track));
        String row;
        boolean firstRow = true;
        try {
            while ((row = reader.readLine()) != null) {
                if (!firstRow) {
                    json.append(",\n");
                }
                json.append("[");
                for (char c : row.toCharArray()) {
                    json.append(convertChar(c)).append(",");
                }
                // delete last ,
                json.deleteCharAt(json.length() - 1);
                json.append("]");
                firstRow = false;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid track", e);
        }

        json.append("\n]");
        return json.toString();

    }

    private int convertChar(char c) {

        // Map characters to int for Quintus
        // ' ' -> 1 = asphalt
        // . -> 2 = grass
        // # -> 3 = wall
        // f -> 4 = finish line
        // A-Z -> 5 = start position
        // 1 -> 6 = sector line 1
        // 2 -> 7 = sector line 2

        int result;
        if (Character.isAlphabetic(c) && Character.isUpperCase(c)) {
            result = 5;
        } else {
            switch (c) {
                case ' ':
                    result = 1;
                    break;
                case '.':
                    result = 2;
                    break;
                case '#':
                    result = 3;
                    break;
                case 'f':
                    result = 4;
                    break;
                case '1':
                    result = 6;
                    break;
                case '2':
                    result = 7;
                    break;
                default:
                    throw new IllegalArgumentException("invalid char: " + c);
            }
        }

        return result;
    }

}
