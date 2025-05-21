package com.weddingplanner.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.weddingplanner.models.Vendor;
import jakarta.servlet.ServletContext;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import java.util.logging.Level;

public class VendorRepository {
    private static final Logger logger = Logger.getLogger(VendorRepository.class.getName());
    private static final String APP_DATA_DIR = "wedding_planner_data";
    private final String dataFilePath;
    private final ObjectMapper mapper;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public VendorRepository(ServletContext context) {
        try {
            // Create data directory in user's home directory
            String userHome = System.getProperty("user.home");
            Path appDataDir = Paths.get(userHome, APP_DATA_DIR);
            Files.createDirectories(appDataDir);

            // Set the full path to vendors.json
            this.dataFilePath = appDataDir.resolve("vendors.json").toString();
            logger.info("Data file path: " + dataFilePath);

            // Initialize ObjectMapper with pretty printing and configure it
            mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            // Configure to ignore unknown properties
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // Create empty vendors.json if it doesn't exist
            File vendorsFile = new File(dataFilePath);
            if (!vendorsFile.exists()) {
                writeEmptyVendorList(vendorsFile);
            } else {
                // Try to read and validate existing file
                try {
                    List<Vendor> existingVendors = mapper.readValue(vendorsFile, new TypeReference<List<Vendor>>() {});
                    // If read successful, write back to ensure proper format
                    mapper.writeValue(vendorsFile, existingVendors);
                    logger.info("Successfully validated existing vendors file");
                } catch (IOException e) {
                    logger.warning("Existing vendors file is invalid, creating backup and starting fresh");
                    Path backupPath = Paths.get(dataFilePath + ".invalid-" + System.currentTimeMillis());
                    Files.move(vendorsFile.toPath(), backupPath);
                    writeEmptyVendorList(vendorsFile);
                }
            }

            // Verify file permissions
            if (!Files.isWritable(Paths.get(dataFilePath))) {
                // Try to make the file writable
                File file = new File(dataFilePath);
                if (!file.setWritable(true)) {
                    throw new RuntimeException("Cannot make vendors.json writable: " + dataFilePath);
                }
            }

            // Try to read the file to verify it's accessible
            try {
                findAll();
                logger.info("Successfully verified data file access");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to verify data file access", e);
                throw new RuntimeException("Cannot access vendors.json: " + e.getMessage(), e);
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize vendor repository", e);
            throw new RuntimeException("Failed to initialize vendor repository: " + e.getMessage(), e);
        }
    }

    private void writeEmptyVendorList(File file) throws IOException {
        List<Vendor> emptyList = new ArrayList<>();
        try {
            mapper.writeValue(file, emptyList);
            // Verify the file was written correctly
            mapper.readValue(file, new TypeReference<List<Vendor>>() {});
            logger.info("Successfully initialized empty vendor list");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize empty vendor list", e);
            throw e;
        }
    }

    public void save(Vendor vendor) throws IOException {
        if (vendor == null) {
            throw new IllegalArgumentException("Vendor cannot be null");
        }
        if (vendor.getId() == null || vendor.getId().isBlank()) {
            throw new IllegalArgumentException("Vendor ID cannot be null or blank");
        }

        lock.writeLock().lock();
        try {
            // Read existing vendors
            List<Vendor> vendors = findAll();
            logger.info("Current vendor count: " + vendors.size());

            // Remove existing vendor if updating
            vendors.removeIf(v -> v.getId().equals(vendor.getId()));

            // Add the new/updated vendor
            vendors.add(vendor);
            logger.info("New vendor count: " + vendors.size());

            // Create temporary file in the same directory
            Path originalPath = Paths.get(dataFilePath);
            Path tempFile = originalPath.resolveSibling("vendors.json.tmp");

            try {
                // Write vendors to temp file
                String jsonContent = mapper.writeValueAsString(vendors);
                Files.writeString(tempFile, jsonContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                // Verify the written content
                try {
                    List<Vendor> verifiedVendors = mapper.readValue(tempFile.toFile(), new TypeReference<List<Vendor>>() {});
                    if (verifiedVendors.size() != vendors.size()) {
                        throw new IOException("Verification failed: vendor count mismatch");
                    }
                    logger.info("Successfully verified written data");
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Failed to verify written data", e);
                    Files.deleteIfExists(tempFile);
                    throw new IOException("Failed to verify written data: " + e.getMessage());
                }

                // Create backup of existing file
                Path backupPath = originalPath.resolveSibling("vendors.json.bak");
                if (Files.exists(originalPath)) {
                    Files.copy(originalPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
                }

                // Replace original file with temp file
                Files.move(tempFile, originalPath, StandardCopyOption.REPLACE_EXISTING);
                logger.info("Successfully saved vendor data");

                // Clean up backup file
                Files.deleteIfExists(backupPath);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to save vendor data", e);
                // Clean up temp file if it exists
                Files.deleteIfExists(tempFile);
                throw new IOException("Failed to save vendor data: " + e.getMessage());
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Vendor> findAll() throws IOException {
        lock.readLock().lock();
        try {
            File file = new File(dataFilePath);
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            try {
                return mapper.readValue(file, new TypeReference<List<Vendor>>() {});
            } catch (IOException e) {
                // If JSON is corrupted, try to recover from backup
                Path backupPath = Paths.get(dataFilePath + ".bak");
                if (Files.exists(backupPath)) {
                    try {
                        return mapper.readValue(backupPath.toFile(), new TypeReference<List<Vendor>>() {});
                    } catch (IOException ignored) {
                        // If backup also fails, return empty list
                    }
                }
                return new ArrayList<>();
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public Vendor findById(String id) throws IOException {
        if (id == null || id.isBlank()) {
            return null;
        }

        lock.readLock().lock();
        try {
            return findAll().stream()
                    .filter(v -> v.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void delete(String id) throws IOException {
        if (id == null || id.isBlank()) {
            return;
        }

        lock.writeLock().lock();
        try {
            List<Vendor> vendors = findAll();
            if (vendors.removeIf(v -> v.getId().equals(id))) {
                // Use the same safe save mechanism as in save()
                Path tempFile = Paths.get(dataFilePath + ".tmp");
                mapper.writeValue(tempFile.toFile(), vendors);
                Path originalPath = Paths.get(dataFilePath);
                Files.move(tempFile, originalPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Find vendors by their serviceType (e.g. "Catering")
     */
    public List<Vendor> findByServiceType(String serviceType) throws IOException {
        if (serviceType == null || serviceType.isBlank()) {
            return new ArrayList<>();
        }

        lock.readLock().lock();
        try {
            return findAll().stream()
                    .filter(v -> serviceType.equals(v.getServiceType()))
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }

    //  filter by price, parse priceRange or redesign the model.
    // public List<Vendor> findByPriceRange(double minPrice, double maxPrice) throws IOException {
    //     return findAll().stream()
    //             .filter(v -> /* parse and compare priceRange here */)
    //             .collect(Collectors.toList());
    // }
}
