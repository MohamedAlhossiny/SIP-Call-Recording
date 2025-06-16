package com.iti.textcom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

@Entity
@Table(name = "calllogs")
public class CallLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CallID")
    private Long callId;
    
    @Column(name = "StartTimestamp")
    private LocalDateTime startTimestamp;
    
    @Column(name = "EndTimestamp")
    private LocalDateTime endTimestamp;
    
    @Column(name = "CallingParty")
    private String callingParty;
    
    @Column(name = "CalledParty")
    private String calledParty;
    
    @Column(name = "Path_WAV_File")
    private String pathWavFile;
    
    @Column(name = "IsCallTransferred")
    private Boolean isCallTransferred;
    
    @OneToMany(mappedBy = "callLog")
    private List<TransactionLog> transactionLogs;
    
    // Default constructor
    public CallLog() {
    }
    
    // Constructor with all fields
    public CallLog(LocalDateTime startTimestamp, LocalDateTime endTimestamp, 
                  String callingParty, String calledParty, String pathWavFile, 
                  Boolean isCallTransferred) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.callingParty = callingParty;
        this.calledParty = calledParty;
        this.pathWavFile = pathWavFile;
        this.isCallTransferred = isCallTransferred;
    }
    
    @Transient
    public String getDuration() {
        if (startTimestamp == null || endTimestamp == null) {
            return "N/A";
        }
        
        Duration duration = Duration.between(startTimestamp, endTimestamp);
        long seconds = duration.getSeconds();
        
        if (seconds < 60) {
            return seconds + " seconds";
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            long remainingSeconds = seconds % 60;
            return minutes + " min " + remainingSeconds + " sec";
        } else {
            long hours = seconds / 3600;
            long minutes = (seconds % 3600) / 60;
            long remainingSeconds = seconds % 60;
            return hours + " hr " + minutes + " min " + remainingSeconds + " sec";
        }
    }
    
    // Getters and Setters
    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }

    public LocalDateTime getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(LocalDateTime startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public LocalDateTime getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(LocalDateTime endTimestamp) {
        this.endTimestamp = endTimestamp;
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

    public String getPathWavFile() {
        return pathWavFile;
    }

    public void setPathWavFile(String pathWavFile) {
        this.pathWavFile = pathWavFile;
    }

    public Boolean getIsCallTransferred() {
        return isCallTransferred;
    }

    public void setIsCallTransferred(Boolean isCallTransferred) {
        this.isCallTransferred = isCallTransferred;
    }

    public List<TransactionLog> getTransactionLogs() {
        return transactionLogs;
    }

    public void setTransactionLogs(List<TransactionLog> transactionLogs) {
        this.transactionLogs = transactionLogs;
    }

    @Override
    public String toString() {
        return "CallLog{" +
                "callId=" + callId +
                ", startTimestamp=" + startTimestamp +
                ", endTimestamp=" + endTimestamp +
                ", callingParty='" + callingParty + '\'' +
                ", calledParty='" + calledParty + '\'' +
                ", pathWavFile='" + pathWavFile + '\'' +
                ", isCallTransferred=" + isCallTransferred +
                ", transactionLogs=" + (transactionLogs != null ? transactionLogs.size() + " items" : "null") +
                '}';
    }
} 