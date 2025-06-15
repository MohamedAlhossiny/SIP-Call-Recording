package com.iti.textcom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "transactionlogs")
public class TransactionLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private Long transactionId;
    
    @ManyToOne
    @JoinColumn(name = "CallID")
    @JsonIgnore
    private CallLog callLog;
    
    @Column(name = "Text")
    private String text;
    
    @Column(name = "Timestamp")
    private LocalDateTime timestamp;
    
    // Default constructor
    public TransactionLog() {
    }
    
    // Constructor with all fields
    public TransactionLog(CallLog callLog, String text, LocalDateTime timestamp) {
        this.callLog = callLog;
        this.text = text;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public CallLog getCallLog() {
        return callLog;
    }

    public void setCallLog(CallLog callLog) {
        this.callLog = callLog;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TransactionLog{" +
                "transactionId=" + transactionId +
                ", callLog=" + (callLog != null ? callLog.getCallId() : "null") +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
} 