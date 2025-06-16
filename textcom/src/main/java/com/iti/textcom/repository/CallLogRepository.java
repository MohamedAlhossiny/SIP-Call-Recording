package com.iti.textcom.repository;

import com.iti.textcom.entity.CallLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CallLogRepository extends JpaRepository<CallLog, Long> {
    // Find calls by calling party
    List<CallLog> findByCallingParty(String callingparty);
    
    // Find calls by called party
    List<CallLog> findByCalledParty(String calledparty);
    
    // Find calls between two timestamps
    List<CallLog> findByStartTimestampBetween(LocalDateTime start, LocalDateTime end);
    
    // Find transferred calls
    List<CallLog> findByIsCallTransferredTrue();
    
    // Find calls by calling party and date range
    List<CallLog> findByCallingPartyAndStartTimestampBetween(
        String callingparty, LocalDateTime start, LocalDateTime end);
        
    // Find the most recent call
    Optional<CallLog> findTopByOrderByCallIdDesc();
    
    // Find calls by either calling or called party, ordered by callId descending
    List<CallLog> findByCallingPartyOrCalledPartyOrderByCallIdDesc(String callingParty, String calledParty);
} 