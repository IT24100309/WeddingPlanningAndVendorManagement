// src/main/java/com/weddingplanner/services/PaymentService.java
package com.weddingplanner.services;

import com.weddingplanner.models.Payment;
import com.weddingplanner.repositories.PaymentRepository;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.util.List;

public class PaymentService {
    private final PaymentRepository repository;

    public PaymentService(ServletContext context) {
        this.repository = new PaymentRepository(context);
    }

    /** Expose save as public so servlets can call it **/
    public void save(Payment payment) throws IOException {
        repository.save(payment);
    }

    public Payment findById(String id) throws IOException {
        return repository.findById(id);
    }

    public List<Payment> findAll() throws IOException {
        return repository.findAll();
    }

    public List<Payment> findByBookingId(String bookingId) throws IOException {
        return repository.findByBookingId(bookingId);
    }

    public List<Payment> findByMethod(String method) throws IOException {
        return repository.findByMethod(method);
    }

    public void update(Payment payment) throws IOException {
        repository.update(payment);
    }

    public void delete(String id) throws IOException {
        repository.delete(id);
    }
}
