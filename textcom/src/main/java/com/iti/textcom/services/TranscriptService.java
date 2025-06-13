package com.iti.textcom.services;

import com.iti.textcom.dto.TranscriptDTO;
import com.iti.textcom.entity.CallLog;
import com.iti.textcom.entity.TransactionLog;
import com.iti.textcom.repository.CallLogRepository;
import com.iti.textcom.repository.TransactionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranscriptService {
    
    @Autowired
    private CallLogRepository callLogRepository;
    
    @Autowired
    private TransactionLogRepository transactionLogRepository;
    
    public List<TranscriptDTO> getAllTranscripts() {
        List<TransactionLog> transactionLogs = transactionLogRepository.findAll();
        return transactionLogs.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public TranscriptDTO getTranscriptById(Long transactionId) {
        TransactionLog transactionLog = transactionLogRepository.findById(transactionId)
            .orElseThrow(() -> new RuntimeException("Transcript not found"));
        return convertToDTO(transactionLog);
    }
    
    private TranscriptDTO convertToDTO(TransactionLog transactionLog) {
        TranscriptDTO dto = new TranscriptDTO();
        CallLog callLog = transactionLog.getCallLog();
        
        dto.setTransactionId(transactionLog.getTransactionId());
        dto.setCallId(callLog.getCallId());
        dto.setCallingParty(callLog.getCallingParty());
        dto.setCalledParty(callLog.getCalledParty());
        dto.setTimestamp(transactionLog.getTimestamp());
        dto.setText(transactionLog.getText());
        dto.setDuration(callLog.getDuration());
        dto.setIsCallTransferred(callLog.getIsCallTransferred());
        
        return dto;
    }
} 