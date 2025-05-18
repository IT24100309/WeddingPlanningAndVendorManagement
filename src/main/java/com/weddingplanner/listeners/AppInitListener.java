package com.weddingplanner.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.weddingplanner.models.Vendor;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class AppInitListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        
        // Initialize sample vendor data if needed
        initSampleVendorData(context);
        
        // Initialize bookings data file
        initBookingsData(context);
        
        // Initialize payments data file
        initPaymentsData(context);
    }
    
    private void initBookingsData(ServletContext context) {
        try {
            String dataFilePath = context.getRealPath("/Data/bookings.json");
            File file = new File(dataFilePath);
            
            // If directory doesn't exist, create it
            File dataDir = file.getParentFile();
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            // Only create empty array if file doesn't exist
            if (!file.exists() || file.length() == 0) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.writeValue(file, new ArrayList<>());
                
                System.out.println("Empty bookings.json file initialized at: " + dataFilePath);
            }
        } catch (Exception e) {
            System.err.println("Error initializing bookings data file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void initPaymentsData(ServletContext context) {
        try {
            String dataFilePath = context.getRealPath("/Data/payments.json");
            File file = new File(dataFilePath);
            
            // If directory doesn't exist, create it
            File dataDir = file.getParentFile();
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            // Only create empty array if file doesn't exist
            if (!file.exists() || file.length() == 0) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.writeValue(file, new ArrayList<>());
                
                System.out.println("Empty payments.json file initialized at: " + dataFilePath);
            }
        } catch (Exception e) {
            System.err.println("Error initializing payments data file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void initSampleVendorData(ServletContext context) {
        try {
            String dataFilePath = context.getRealPath("/Data/vendors.json");
            File file = new File(dataFilePath);
            
            // If directory doesn't exist, create it
            File dataDir = file.getParentFile();
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            // Only create sample data if file doesn't exist
            if (!file.exists() || file.length() == 0) {
                List<Vendor> sampleVendors = new ArrayList<>();
                
                // Sample vendor 1
                Vendor vendor1 = new Vendor();
                vendor1.setId("sample-vendor");
                vendor1.setName("Sample Vendor");
                vendor1.setServiceType("Photography");
                vendor1.setDescription("Professional wedding photography services");
                vendor1.setContactPerson("John Doe");
                vendor1.setPhoneNumber("123-456-7890");
                vendor1.setEmail("sample@vendor.com");
                vendor1.setWebsite("www.samplevendor.com");
                vendor1.setAddress("123 Wedding St, Photography City");
                vendor1.setPriceRange("15000-25000");
                vendor1.setAvailability("Weekends");
                vendor1.setRating(4.5);
                vendor1.setReviews(new ArrayList<>());
                vendor1.setPortfolio(new ArrayList<>());
                vendor1.setTerms("50% advance payment required");
                vendor1.setCreatedAt(LocalDateTime.now());
                vendor1.setUpdatedAt(LocalDateTime.now());
                vendor1.setStatus("Active");
                
                // Sample vendor 2
                Vendor vendor2 = new Vendor();
                vendor2.setId("catering-service");
                vendor2.setName("Delicious Catering");
                vendor2.setServiceType("Catering");
                vendor2.setDescription("Exquisite food for your special day");
                vendor2.setContactPerson("Jane Smith");
                vendor2.setPhoneNumber("987-654-3210");
                vendor2.setEmail("info@deliciouscatering.com");
                vendor2.setWebsite("www.deliciouscatering.com");
                vendor2.setAddress("456 Food Ave, Tasty Town");
                vendor2.setPriceRange("30000-50000");
                vendor2.setAvailability("All days");
                vendor2.setRating(4.8);
                vendor2.setReviews(new ArrayList<>());
                vendor2.setPortfolio(new ArrayList<>());
                vendor2.setTerms("30% advance payment required");
                vendor2.setCreatedAt(LocalDateTime.now());
                vendor2.setUpdatedAt(LocalDateTime.now());
                vendor2.setStatus("Active");
                
                sampleVendors.add(vendor1);
                sampleVendors.add(vendor2);
                
                // Save to file
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.writeValue(file, sampleVendors);
                
                System.out.println("Sample vendor data initialized at: " + dataFilePath);
            }
        } catch (Exception e) {
            System.err.println("Error initializing sample vendor data: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 