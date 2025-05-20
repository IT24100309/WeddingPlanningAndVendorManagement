package com.weddingplanner.models;

import java.util.UUID;

public class User {
    private final String id;
    private String username;
    private String passwordHash;
    private String email;

    public User(String username, String passwordHash, String email) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
    }



    public User(String id, String username, String passwordHash, String email) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
    }



    public String getId()           { return id; }
    public String getUsername()     { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getEmail()        { return email; }

    public void setUsername(String u)       { this.username = u; }
    public void setPasswordHash(String ph)  { this.passwordHash = ph; }
    public void setEmail(String e)          { this.email = e; }

    @Override
    public String toString() {
        // CSV: id,username,passwordHash,email
        return String.join(",", id, username, passwordHash, email);
    }
}
