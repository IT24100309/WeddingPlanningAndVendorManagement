// src/main/java/com/weddingplanner/services/BookingService.java
package com.weddingplanner.services;

import com.weddingplanner.models.Booking;
import com.weddingplanner.repositories.BookingRepository;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.util.List;

public class BookingService {
    private final BookingRepository repository;

    public BookingService(ServletContext context) {
        this.repository = new BookingRepository(context);
    }

    /** Expose save as public so servlets can call it **/
    public void save(Booking booking) throws IOException {
        repository.save(booking);
    }

    public Booking findById(String id) throws IOException {
        return repository.findById(id);
    }

    public List<Booking> findAll() throws IOException {
        return repository.findAll();
    }

    public List<Booking> findByCustomerName(String customerName) throws IOException {
        return repository.findByCustomerName(customerName);
    }

    public List<Booking> findByVendorName(String vendorName) throws IOException {
        return repository.findByVendorName(vendorName);
    }

    public void update(Booking booking) throws IOException {
        repository.update(booking);
    }

    public void delete(String id) throws IOException {
        repository.delete(id);
    }
}
