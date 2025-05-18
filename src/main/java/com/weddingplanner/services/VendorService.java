// src/main/java/com/weddingplanner/services/VendorService.java
package com.weddingplanner.services;

import com.weddingplanner.models.Vendor;
import com.weddingplanner.repositories.VendorRepository;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.util.List;

public class VendorService {
    private final VendorRepository repository;

    public VendorService(ServletContext context) {
        this.repository = new VendorRepository(context);
    }

    /** Create or update a vendor **/
    public void save(Vendor vendor) throws IOException {
        repository.save(vendor);
    }

    /** Lookup by ID (in this case, name) **/
    public Vendor findById(String id) throws IOException {
        return repository.findById(id);
    }

    /** Return all vendors **/
    public List<Vendor> findAll() throws IOException {
        return repository.findAll();
    }

    /** Return vendors matching a specific serviceType **/
    public List<Vendor> findByServiceType(String serviceType) throws IOException {
        return repository.findByServiceType(serviceType);
    }

    // If you want to filter by price, you need to parse priceRange or redesign the model.
    // public List<Vendor> findByPriceRange(double minPrice, double maxPrice) throws IOException {
    //     return repository.findByPriceRange(minPrice, maxPrice);
    // }

    /** Delete a vendor **/
    public void delete(String id) throws IOException {
        repository.delete(id);
    }

    // Add more methods as needed (e.g., findByServiceType, update, etc.)
}
