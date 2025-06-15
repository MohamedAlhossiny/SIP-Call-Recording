package com.iti.textcom.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iti.textcom.entity.Accounts;
import com.iti.textcom.entity.CallLog;
import com.iti.textcom.entity.TransactionLog;
import com.iti.textcom.repository.CallLogRepository;
import com.iti.textcom.repository.TransactionLogRepository;
import com.iti.textcom.services.SessionService;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private SessionService sessionService;
    
    @Autowired
    private CallLogRepository callLogRepository;
    
    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @GetMapping("/user/profile")
    public String showProfile(HttpSession session, Model model) {
        if (!sessionService.isUserLoggedIn(session)) {
            return "redirect:/accounts/login";
        }
        Accounts account = sessionService.getLoggedInUser(session);
        model.addAttribute("username", account.getUsername());
        model.addAttribute("fullName", account.getFullName());
        model.addAttribute("email", account.getEmail());
        model.addAttribute("misdsn", account.getMisdsn());
        model.addAttribute("role", account.getRole());
        return "user/profile";
    }

    @GetMapping("/user/calls")
    public String showCalls(HttpSession session, Model model) {
        if (!sessionService.isUserLoggedIn(session)) {
            return "redirect:/accounts/login";
        }
        Accounts account = sessionService.getLoggedInUser(session);
        model.addAttribute("username", account.getUsername());
        
        // Get calls for the logged-in user, ordered by callId descending
        List<CallLog> calls = callLogRepository.findByCallingPartyOrCalledPartyOrderByCallIdDesc(
            account.getMisdsn(), 
            account.getMisdsn()
        );
        model.addAttribute("calls", calls);
        
        return "user/calls";
    }
    
    @GetMapping("/user/transcripts")
    public String showTranscripts(HttpSession session, Model model) {
        if (!sessionService.isUserLoggedIn(session)) {
            return "redirect:/accounts/login";
        }
        Accounts account = sessionService.getLoggedInUser(session);
        model.addAttribute("username", account.getUsername());
        
        // Get transcripts for the logged-in user's calls
        List<TransactionLog> transcripts = transactionLogRepository.findByCallLog_CallingPartyOrCallLog_CalledPartyOrderByTimestampDesc(
            account.getMisdsn(),
            account.getMisdsn()
        );
        model.addAttribute("transcripts", transcripts);
        
        return "user/transcripts";
    }

    @GetMapping("/user/call-details/{callId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCallDetails(@PathVariable Long callId, HttpSession session) {
        if (!sessionService.isUserLoggedIn(session)) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        Accounts account = sessionService.getLoggedInUser(session);

        Optional<CallLog> callLogOptional = callLogRepository.findById(callId);

        if (callLogOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CallLog call = callLogOptional.get();

        // Ensure the call belongs to the logged-in user
        if (!call.getCallingParty().equals(account.getMisdsn()) && !call.getCalledParty().equals(account.getMisdsn())) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        List<TransactionLog> transactionLogs = transactionLogRepository.findByCallLog_CallIdOrderByTimestampAsc(callId);

        Map<String, Object> response = new HashMap<>();
        response.put("callId", call.getCallId());
        response.put("startTimestamp", call.getStartTimestamp());
        response.put("endTimestamp", call.getEndTimestamp());
        response.put("callingParty", call.getCallingParty());
        response.put("calledParty", call.getCalledParty());
        response.put("pathWavFile", call.getPathWavFile());
        response.put("isCallTransferred", call.getIsCallTransferred());
        response.put("transactionLogs", transactionLogs);

        return ResponseEntity.ok(response);
    }
}
