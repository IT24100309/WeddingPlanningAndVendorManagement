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

    //Create , update  vendor
    public void save(Vendor vendor) throws IOException {
        repository.save(vendor);
    }

    // Lookup by ID ( name)
    public Vendor findById(String id) throws IOException {
        return repository.findById(id);
    }

    // Return all vendors
    public List<Vendor> findAll() throws IOException {
        return repository.findAll();
    }

    // Return vendors matching a specific serviceType
    public List<Vendor> findByServiceType(String serviceType) throws IOException {
        return repository.findByServiceType(serviceType);
    }


    /** Delete vendor **/
    public void delete(String id) throws IOException {
        repository.delete(id);
    }


}
