package com.iti.textcom.dto;

import java.time.LocalDateTime;

public class TranscriptDTO {
    private Long transactionId;
    private Long callId;
    private String callingParty;
    private String calledParty;
    private LocalDateTime timestamp;
    private String text;
    private String duration;
    private Boolean isCallTransferred;

    // Default constructor
    public TranscriptDTO() {
    }

    // Getters and Setters
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }

    public String getCallingParty() {
        return callingParty;
    }

    public void setCallingParty(String callingParty) {
        this.callingParty = callingParty;
    }

    public String getCalledParty() {
        return calledParty;
    }

    public void setCalledParty(String calledParty) {
        this.calledParty = calledParty;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Boolean getIsCallTransferred() {
        return isCallTransferred;
    }

    public void setIsCallTransferred(Boolean isCallTransferred) {
        this.isCallTransferred = isCallTransferred;
    }
} 