package com.weddingplanner.models;

import java.time.LocalDate;
import java.util.UUID;

public class Booking {
    private String id;
    private String vendorName;
    private String customerName;
    private LocalDate eventDate;
    private double price;
    
    // Default constructor for Jackson deserialization
    public Booking() {
        this.id = UUID.randomUUID().toString();
    }

    public Booking(String vendorName, String customerName,
                   LocalDate eventDate, double price) {
        this.id = UUID.randomUUID().toString();
        this.vendorName = vendorName;
        this.customerName = customerName;
        this.eventDate = eventDate;
        this.price = price;
    }

    public Booking(String id, String vendorName, String customerName,
                   LocalDate eventDate, double price) {
        this.id = id;
        this.vendorName = vendorName;
        this.customerName = customerName;
        this.eventDate = eventDate;
        this.price = price;
    }

    public String getId()                { return id; }
    public String getVendorName()        { return vendorName; }
    public String getCustomerName()      { return customerName; }
    public LocalDate getEventDate()      { return eventDate; }
    public double getPrice()             { return price; }

    public void setVendorName(String v)   { this.vendorName = v; }
    public void setCustomerName(String c) { this.customerName = c; }
    public void setEventDate(LocalDate d) { this.eventDate = d; }
    public void setPrice(double p)        { this.price = p; }

    private String escapeCsv(String field) {
        if (field == null) return "";
        boolean needsQuotes = field.contains(",") || field.contains("\"");
        if (needsQuotes) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }

    @Override
    public String toString() {
        return String.join(",",
                id,
                escapeCsv(vendorName),
                escapeCsv(customerName),
                eventDate.toString(),
                Double.toString(price)
        );
    }
}
