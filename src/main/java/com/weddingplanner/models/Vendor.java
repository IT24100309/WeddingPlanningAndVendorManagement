package com.weddingplanner.models;

import java.time.LocalDateTime;
import java.util.List;

public class Vendor {
    private String id;
    private String name;
    private String serviceType;
    private String description;
    private String contactPerson;
    private String phoneNumber;
    private String email;
    private String website;
    private String address;
    private String priceRange;
    private String availability; // Could be a string or a more complex type
    private double rating;
    private List<String> reviews; // Or a List<Review> if you have a Review class
    private List<String> portfolio; // List of image URLs or file paths
    private String terms;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status; // "Active" or "Inactive"

    public Vendor() {}

    public Vendor(String id, String name, String serviceType, String description, String contactPerson,
                  String phoneNumber, String email, String website, String address, String priceRange,
                  String availability, double rating, List<String> reviews, List<String> portfolio,
                  String terms, LocalDateTime createdAt, LocalDateTime updatedAt, String status) {
        this.id = id;
        this.name = name;
        this.serviceType = serviceType;
        this.description = description;
        this.contactPerson = contactPerson;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.website = website;
        this.address = address;
        this.priceRange = priceRange;
        this.availability = availability;
        this.rating = rating;
        this.reviews = reviews;
        this.portfolio = portfolio;
        this.terms = terms;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public List<String> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(List<String> portfolio) {
        this.portfolio = portfolio;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return serviceType + " by " + name + " @ Rs." + priceRange;
    }
}
