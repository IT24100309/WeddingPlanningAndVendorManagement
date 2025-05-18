package com.weddingplanner.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.weddingplanner.models.Vendor;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VendorRepository {
    private static final String DATA_FILE_PATH = "/Data/vendors.json";
    private final String dataFilePath;
    private final ObjectMapper mapper;

    public VendorRepository(ServletContext context) {
        this.dataFilePath = context.getRealPath(DATA_FILE_PATH);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // Ensure the directory exists
        File dataDir = new File(dataFilePath).getParentFile();
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    public void save(Vendor vendor) throws IOException {
        List<Vendor> vendors = findAll();
        // Remove old if updating
        vendors.removeIf(v -> v.getId().equals(vendor.getId()));
        vendors.add(vendor);
        mapper.writeValue(new File(dataFilePath), vendors);
    }

    public Vendor findById(String id) throws IOException {
        return findAll().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Vendor> findAll() throws IOException {
        File file = new File(dataFilePath);
        
        try {
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(file, new TypeReference<List<Vendor>>() {});
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

    public void delete(String id) throws IOException {
        List<Vendor> vendors = findAll();
        vendors.removeIf(v -> v.getId().equals(id));
        mapper.writeValue(new File(dataFilePath), vendors);
    }

    /**
     * Find vendors by their serviceType (e.g. "Catering")
     */
    public List<Vendor> findByServiceType(String serviceType) throws IOException {
        return findAll().stream()
                .filter(v -> v.getServiceType().equals(serviceType))
                .collect(Collectors.toList());
    }

    // If you want to filter by price, you need to parse priceRange or redesign the model.
    // public List<Vendor> findByPriceRange(double minPrice, double maxPrice) throws IOException {
    //     return findAll().stream()
    //             .filter(v -> /* parse and compare priceRange here */)
    //             .collect(Collectors.toList());
    // }
}
