package com.iti.textcom.dto.activity;

import com.iti.textcom.entity.TransactionLog;

public class RecentActivityDTO {
    private String description;
    private String timestamp;

    // Getters and Setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
