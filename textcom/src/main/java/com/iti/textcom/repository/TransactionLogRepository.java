package com.iti.textcom.repository;

import com.iti.textcom.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
    // Find transactions by call ID
    List<TransactionLog> findByCallLog_CallId(Long callId);
    
    // Find transactions between two timestamps
    List<TransactionLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    
    // Find transactions containing specific text
    List<TransactionLog> findByTextContaining(String text);
    
    // Find transactions by call ID and date range
    List<TransactionLog> findByCallLog_CallIdAndTimestampBetween(
        Long callId, LocalDateTime start, LocalDateTime end);
        
    // Find transcripts for calls where the user is either calling or called party
    List<TransactionLog> findByCallLog_CallingPartyOrCallLog_CalledPartyOrderByTimestampDesc(
        String callingParty, String calledParty);
} 