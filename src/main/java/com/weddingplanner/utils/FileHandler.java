package com.weddingplanner.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;

public class FileHandler {
    public List<String> readFromFile(String fileName) {
        CustomLinkedList<String> lines = new CustomLinkedList<>();
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
        
        // Convert CustomLinkedList to java.util.List for compatibility with existing code
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            result.add(line);
        }
        return result;
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
