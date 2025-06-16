package com.iti.textcom.dto.system;

public class SystemStatusDTO {
    private String serverStatus;
    private String databaseStatus;
    private String storageStatus;

    // Getters and Setters
    public String getServerStatus() { return serverStatus; }
    public void setServerStatus(String serverStatus) { this.serverStatus = serverStatus; }
    public String getDatabaseStatus() { return databaseStatus; }
    public void setDatabaseStatus(String databaseStatus) { this.databaseStatus = databaseStatus; }
    public String getStorageStatus() { return storageStatus; }
    public void setStorageStatus(String storageStatus) { this.storageStatus = storageStatus; }
}
