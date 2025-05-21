package com.weddingplanner.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.weddingplanner.models.Payment;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentRepository {
    
    private final String dataFilePath;
    private final ObjectMapper mapper;
    
    public PaymentRepository(ServletContext context) {
        this.dataFilePath = context.getRealPath("/Data/payments.json");
        
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
    
    public void save(Payment payment) throws IOException {
        List<Payment> payments = findAll();
        // Remove old if updating
        payments.removeIf(p -> p.getId().equals(payment.getId()));
        payments.add(payment);
        mapper.writeValue(new File(dataFilePath), payments);
    }
    
    public Payment findById(String id) throws IOException {
        return findAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Payment> findAll() throws IOException {
        File file = new File(dataFilePath);
        
        try {
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(file, new TypeReference<List<Payment>>() {});
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
    
    public void update(Payment payment) throws IOException {
        save(payment);  // Since we use the same mechanism for saving
    }
    
    public void delete(String id) throws IOException {
        List<Payment> payments = findAll();
        payments.removeIf(p -> p.getId().equals(id));
        mapper.writeValue(new File(dataFilePath), payments);
    }

    /** Lookup by bookingId */
    public List<Payment> findByBookingId(String bookingId) throws IOException {
        return findAll().stream()
                .filter(p -> p.getBookingId().equals(bookingId))
                .collect(Collectors.toList());
    }

    /** Lookup by payment method */
    public List<Payment> findByMethod(String method) throws IOException {
        return findAll().stream()
                .filter(p -> p.getMethod().equals(method))
                .collect(Collectors.toList());
    }
}
