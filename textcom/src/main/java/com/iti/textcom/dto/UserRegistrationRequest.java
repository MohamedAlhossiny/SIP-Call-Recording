package com.iti.textcom.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserRegistrationRequest {

    @NotEmpty
    private String misdsn;

    @NotEmpty
    private String password;

    @Email
    private String email;

    private String address;

    // Getters and Setters
    public String getMisdsn() {
        return misdsn;
    }

    public void setMisdsn(String misdsn) {
        this.misdsn = misdsn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}