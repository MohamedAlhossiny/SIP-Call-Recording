package com.iti.textcom.dto;

import com.iti.textcom.dto.system.SystemStatusDTO;
import com.iti.textcom.dto.activity.RecentActivityDTO;
import java.util.List;

public class DashboardDTO {
    private long totalUsers;
    private long totalCalls;
    private long totalTranscripts;
    private List<RecentActivityDTO> recentActivities;
    private SystemStatusDTO systemStatus;

     public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalCalls() {
        return totalCalls;
    }

    public void setTotalCalls(long totalCalls) {
        this.totalCalls = totalCalls;
    }

    public long getTotalTranscripts() {
        return totalTranscripts;
    }

    public void setTotalTranscripts(long totalTranscripts) {
        this.totalTranscripts = totalTranscripts;
    }

    public List<RecentActivityDTO> getRecentActivities() {
        return recentActivities;
    }

    public void setRecentActivities(List<RecentActivityDTO> recentActivities) {
        this.recentActivities = recentActivities;
    }

    public SystemStatusDTO getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(SystemStatusDTO systemStatus) {
        this.systemStatus = systemStatus;
    }
}