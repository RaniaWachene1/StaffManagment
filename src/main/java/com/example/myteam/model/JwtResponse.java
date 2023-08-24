package com.example.myteam.model;

public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;  // Set the 'token' field to the value passed as an argument
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // You might also want to add toString, equals, and hashCode methods
}
