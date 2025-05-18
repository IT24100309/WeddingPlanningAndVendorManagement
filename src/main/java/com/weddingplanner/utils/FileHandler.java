package com.weddingplanner.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class FileHandler {
    public List<String> readFromFile(String fileName) {
        List<String> lines = new LinkedList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            try { 
                file.getParentFile().mkdirs(); 
                file.createNewFile(); 
            }
            catch (IOException e) { 
                System.err.println("Failed to create file '" + fileName + "': " + e.getMessage()); 
                throw new RuntimeException("Could not initialize data file", e);
            }
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) lines.add(line);
        } catch (IOException e) {
            System.err.println("Failed to read from file '" + fileName + "': " + e.getMessage());
            throw new RuntimeException("Could not read data file", e);
        }
        return lines;
    }

    public void writeToFile(String fileName, List<String> data) {
        try {
            File f = new File(fileName);
            f.getParentFile().mkdirs();
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
                for (String line : data) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to write to file '" + fileName + "': " + e.getMessage());
            throw new RuntimeException("Could not write to data file", e);
        }
    }
}
