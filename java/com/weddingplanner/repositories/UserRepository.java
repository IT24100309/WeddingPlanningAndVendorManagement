package com.weddingplanner.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.weddingplanner.models.User;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository {
    private final String dataFilePath;
    private final ObjectMapper mapper;
    
    public UserRepository(ServletContext context) {
        this.dataFilePath = context.getRealPath("/Data/users.json");
        
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // Ensure the directory exists
        File dataDir = new File(dataFilePath).getParentFile();
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        // Ensure the file exists with a valid JSON array
        try {
            File file = new File(dataFilePath);
            if (!file.exists() || file.length() == 0) {
                mapper.writeValue(file, new ArrayList<>());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void save(User user) throws IOException {
        List<User> users = findAll();
        // Remove old if updating
        users.removeIf(u -> u.getId().equals(user.getId()));
        users.add(user);
        mapper.writeValue(new File(dataFilePath), users);
    }
    
    public User findById(String id) throws IOException {
        return findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<User> findAll() throws IOException {
        File file = new File(dataFilePath);
        
        try {
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            // If JSON is corrupted, create a backup and return empty list
            if (file.exists()) {
                File backup = new File(dataFilePath + ".backup");
                file.renameTo(backup);
            }
            // Create a new valid empty array
            mapper.writeValue(new File(dataFilePath), new ArrayList<>());
            return new ArrayList<>();
        }
    }
    
    public void update(User user) throws IOException {
        save(user);  // Since we use the same mechanism for saving
    }
    
    public void delete(String id) throws IOException {
        List<User> users = findAll();
        users.removeIf(u -> u.getId().equals(id));
        mapper.writeValue(new File(dataFilePath), users);
    }

    public Optional<User> findByEmail(String email) throws IOException {
        return findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }
} 