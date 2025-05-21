// src/main/java/com/weddingplanner/repositories/BookingRepository.java
package com.weddingplanner.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.weddingplanner.models.Booking;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingRepository {
    
    private final String dataFilePath;
    private final ObjectMapper mapper;
    
    public BookingRepository(ServletContext context) {
        this.dataFilePath = context.getRealPath("/Data/bookings.json");
        
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
    
    public void save(Booking booking) throws IOException {
        List<Booking> bookings = findAll();
        // Remove old if updating
        bookings.removeIf(b -> b.getId().equals(booking.getId()));
        bookings.add(booking);
        mapper.writeValue(new File(dataFilePath), bookings);
    }
    
    public Booking findById(String id) throws IOException {
        return findAll().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Booking> findAll() throws IOException {
        File file = new File(dataFilePath);
        
        try {
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(file, new TypeReference<List<Booking>>() {});
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
    
    public void update(Booking booking) throws IOException {
        save(booking);  // Since we use the same mechanism for saving
    }
    
    public void delete(String id) throws IOException {
        List<Booking> bookings = findAll();
        bookings.removeIf(b -> b.getId().equals(id));
        mapper.writeValue(new File(dataFilePath), bookings);
    }

    /**
     * Returns only those bookings whose customerName matches.
     */
    public List<Booking> findByCustomerName(String customerName) throws IOException {
        return findAll().stream()
                .filter(b -> b.getCustomerName().equals(customerName))
                .collect(Collectors.toList());
    }

    /**
     * Returns only those bookings whose vendorName matches.
     */
    public List<Booking> findByVendorName(String vendorName) throws IOException {
        return findAll().stream()
                .filter(b -> b.getVendorName().equals(vendorName))
                .collect(Collectors.toList());
    }
}
