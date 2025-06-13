package com.iti.textcom.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "accounts")
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10, nullable = false)
    private Role role;

    @NotEmpty(message = "Username is required")
    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @NotEmpty(message = "Password is required")
    
    @Column(name = "passwd", nullable = false)
    private String password;

    @Email(message = "Invalid email format")
    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Pattern(regexp = "^[0-9]{10}$", message = "MISDSN must be exactly 10 digits", groups = UserValidationGroup.class)
    @Column(name = "misdsn", length = 20, unique = true)
    private String misdsn;

    // Constructors
    public Accounts() {
    }

    public Accounts(Role role, String username, String password, String email, String fullName, String misdsn) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.misdsn = misdsn;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMisdsn() {
        return misdsn;
    }

    public void setMisdsn(String misdsn) {
        this.misdsn = misdsn;
    }
} 