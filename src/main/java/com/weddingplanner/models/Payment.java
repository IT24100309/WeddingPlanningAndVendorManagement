package com.weddingplanner.models;

import java.time.LocalDate;
import java.util.UUID;

public class Payment {
    private String id;
    private String bookingId;
    private LocalDate paymentDate;
    private double amount;
    private String method;

    // Default constructor for Jackson deserialization
    public Payment() {
        this.id = UUID.randomUUID().toString();
    }

    public Payment(String bookingId, LocalDate paymentDate, double amount, String method) {
        this.id = UUID.randomUUID().toString();
        this.bookingId = bookingId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.method = method;
    }

    public Payment(String id, String bookingId, LocalDate paymentDate, double amount, String method) {
        this.id = id;
        this.bookingId = bookingId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.method = method;
    }

    public String getId()             { return id; }
    public String getBookingId()      { return bookingId; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public double getAmount()         { return amount; }
    public String getMethod()         { return method; }

    public void setBookingId(String b)        { this.bookingId = b; }
    public void setPaymentDate(LocalDate d)   { this.paymentDate = d; }
    public void setAmount(double a)           { this.amount = a; }
    public void setMethod(String m)           { this.method = m; }

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
                escapeCsv(bookingId),
                paymentDate.toString(),
                Double.toString(amount),
                escapeCsv(method)
        );
    }
}
