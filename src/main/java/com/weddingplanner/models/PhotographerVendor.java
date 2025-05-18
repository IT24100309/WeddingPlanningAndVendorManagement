// PhotographerVendor.java
package com.weddingplanner.models;

import java.time.LocalDateTime;
import java.util.List;

public class PhotographerVendor extends Vendor {
    public PhotographerVendor() {
    }

    public PhotographerVendor(String id, String name, String serviceType, String description, String contactPerson, String phoneNumber, String email, String website, String address, String priceRange, String availability, double rating, List<String> reviews, List<String> portfolio, String terms, LocalDateTime createdAt, LocalDateTime updatedAt, String status) {
        super(id, name, serviceType, description, contactPerson, phoneNumber, email, website, address, priceRange, availability, rating, reviews, portfolio, terms, createdAt, updatedAt, status);
    }

    @Override
    public String getDetails() {
        return super.getDetails();
    }
}
