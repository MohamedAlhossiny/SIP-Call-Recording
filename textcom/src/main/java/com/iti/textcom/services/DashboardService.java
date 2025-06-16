package com.iti.textcom.services;

import com.iti.textcom.dto.*;
import com.iti.textcom.dto.activity.*;
import com.iti.textcom.dto.system.*;

import com.iti.textcom.repository.*;
import com.iti.textcom.entity.*;
import com.iti.textcom.entity.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);

    @Autowired
    private AccountsRepository accountRepository;

    @Autowired
    private CallLogRepository callLogRepository;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    public DashboardDTO getDashboardData() {
        DashboardDTO dashboardDTO = new DashboardDTO();

        // Fetch counts - exclude admin users from total count
        dashboardDTO.setTotalUsers(accountRepository.countByRole(Role.USER));
        dashboardDTO.setTotalCalls(callLogRepository.count());
        dashboardDTO.setTotalTranscripts(transactionLogRepository.count());

        // Combine recent activities from all sources
        List<RecentActivityDTO> recentActivities = new ArrayList<>();

        // Add recent users (in descending order by ID) - exclude admin users
        accountRepository.findByRoleNotOrderByIdDesc(Role.ADMIN).stream()
            .limit(3)
            .map(user -> {
                RecentActivityDTO dto = new RecentActivityDTO();
                dto.setDescription("New User: " + user.getUsername());
                dto.setTimestamp(user.getId().toString()); // Using ID as timestamp since createdAt is not available
                return dto;
            })
            .forEach(recentActivities::add);

        // Add recent calls (in descending order by callId)
        List<CallLog> recentCalls = callLogRepository.findAll(Sort.by(Sort.Direction.DESC, "callId"));
        logger.info("Found {} call logs", recentCalls.size());
        
        recentCalls.stream()
            .limit(3)
            .forEach(call -> {
                logger.info("Call Log: ID={}, CallingParty={}, CalledParty={}, StartTime={}, EndTime={}",
                    call.getCallId(),
                    call.getCallingParty(),
                    call.getCalledParty(),
                    call.getStartTimestamp(),
                    call.getEndTimestamp()
                );
            });

        recentCalls.stream()
            .limit(3)
            .map(call -> {
                RecentActivityDTO dto = new RecentActivityDTO();
                // Get calling and called party numbers
                String callingParty = call.getCallingParty();
                String calledParty = call.getCalledParty();
                
                // Create description based on available information
                StringBuilder description = new StringBuilder("Call ");
                if (callingParty != null && !callingParty.trim().isEmpty()) {
                    description.append("from ").append(callingParty);
                }
                if (calledParty != null && !calledParty.trim().isEmpty()) {
                    description.append(" to ").append(calledParty);
                }
                if (description.toString().equals("Call ")) {
                    description.append("ID: ").append(call.getCallId());
                }
                
                dto.setDescription(description.toString());
                
                // Handle timestamp
                if (call.getStartTimestamp() != null) {
                    dto.setTimestamp(call.getStartTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                } else if (call.getEndTimestamp() != null) {
                    dto.setTimestamp(call.getEndTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                } else {
                    dto.setTimestamp("Call ID: " + call.getCallId());
                }
                return dto;
            })
            .forEach(recentActivities::add);

        // Add recent transcripts (in descending order by transactionId)
        transactionLogRepository.findAll(Sort.by(Sort.Direction.DESC, "transactionId")).stream()
            .limit(3)
            .map(log -> {
                RecentActivityDTO dto = new RecentActivityDTO();
                String text = log.getText() != null ? log.getText() : "No transcript text available";
                dto.setDescription("Transcript: " + text);
                // Handle null timestamp
                if (log.getTimestamp() != null) {
                    dto.setTimestamp(log.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                } else {
                    dto.setTimestamp("Transaction ID: " + log.getTransactionId());
                }
                return dto;
            })
            .forEach(recentActivities::add);

        dashboardDTO.setRecentActivities(recentActivities);

        // Set system status
        SystemStatusDTO systemStatus = new SystemStatusDTO();
        systemStatus.setServerStatus("Online");
        systemStatus.setDatabaseStatus("Connected");
        systemStatus.setStorageStatus("Healthy");
        dashboardDTO.setSystemStatus(systemStatus);

        return dashboardDTO;
    }
}