package com.iti.textcom.controller;

import com.iti.textcom.entity.Accounts;
import com.iti.textcom.entity.CallLog;
import com.iti.textcom.entity.Role;
import com.iti.textcom.services.SessionService;
import com.iti.textcom.services.DashboardService;
import com.iti.textcom.dto.DashboardDTO;
import com.iti.textcom.repository.AccountsRepository;
import com.iti.textcom.repository.CallLogRepository;
import com.iti.textcom.repository.SipFriendRepository;
import com.iti.textcom.services.TranscriptService;
import com.iti.textcom.dto.TranscriptDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.iti.textcom.entity.TransactionLog;
import com.iti.textcom.repository.TransactionLogRepository;

@Controller
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private SessionService sessionService;

    @Autowired
    private DashboardService dashboardService;
    
    @Autowired
    private AccountsRepository accountsRepository;
    
    @Autowired
    private CallLogRepository callLogRepository;
    
    @Autowired
    private TranscriptService transcriptService;

    @Autowired
    private SipFriendRepository sipFriendRepository;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!sessionService.isUserLoggedIn(session)) {
            return "redirect:/accounts/login";
        }
        Accounts account = sessionService.getLoggedInUser(session);
        if (account.getRole() != Role.ADMIN) {
            return "redirect:/user/profile";
        }
        model.addAttribute("username", account.getUsername());
        model.addAttribute("fullName", account.getFullName());
        model.addAttribute("email", account.getEmail());
        model.addAttribute("misdsn", account.getMisdsn());
        model.addAttribute("role", account.getRole());
        
        // Add dashboard data
        DashboardDTO dashboardData = dashboardService.getDashboardData();
        model.addAttribute("dashboard", dashboardData);
        
        return "admin/dashboard";
    }

    @GetMapping("/admin/users")
    public String users(HttpSession session, Model model) {
        if (!sessionService.isUserLoggedIn(session)) {
            return "redirect:/accounts/login";
        }
        Accounts account = sessionService.getLoggedInUser(session);
        if (account.getRole() != Role.ADMIN) {
            return "redirect:/user/profile";
        }
        
        model.addAttribute("username", account.getUsername());
        
        // Get all users
        List<Accounts> users = accountsRepository.findAll();
        model.addAttribute("users", users);
        
        return "admin/users";
    }

    @GetMapping("/admin/calls")
    public String calls(HttpSession session, Model model) {
        if (!sessionService.isUserLoggedIn(session)) {
            return "redirect:/accounts/login";
        }
        Accounts account = sessionService.getLoggedInUser(session);
        if (account.getRole() != Role.ADMIN) {
            return "redirect:/user/profile";
        }
        
        model.addAttribute("username", account.getUsername());
        
        // Get all calls ordered by callId descending
        List<CallLog> calls = callLogRepository.findAll(Sort.by(Sort.Direction.DESC, "callId"));
        model.addAttribute("calls", calls);
        
        return "admin/calls";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!sessionService.isUserLoggedIn(session)) {
            return "redirect:/accounts/login";
        }
        Accounts admin = sessionService.getLoggedInUser(session);
        if (admin.getRole() != Role.ADMIN) {
            return "redirect:/user/profile";
        }

        Optional<Accounts> userOpt = accountsRepository.findById(id);
        if (userOpt.isEmpty()) {
            return "redirect:/admin/users";
        }

        model.addAttribute("username", admin.getUsername());
        model.addAttribute("user", userOpt.get());
        return "admin/edit-user";
    }

    @PostMapping("/admin/users/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute Accounts updatedUser, 
                           HttpSession session, RedirectAttributes redirectAttributes) {
        if (!sessionService.isUserLoggedIn(session)) {
            return "redirect:/accounts/login";
        }
        Accounts admin = sessionService.getLoggedInUser(session);
        if (admin.getRole() != Role.ADMIN) {
            return "redirect:/user/profile";
        }

        Optional<Accounts> existingUserOpt = accountsRepository.findById(id);
        if (existingUserOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/admin/users";
        }

        Accounts existingUser = existingUserOpt.get();
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setMisdsn(updatedUser.getMisdsn());
        existingUser.setRole(updatedUser.getRole());

        accountsRepository.save(existingUser);
        redirectAttributes.addFlashAttribute("success", "User updated successfully");
        return "redirect:/admin/users";
    }

    @Transactional
    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!sessionService.isUserLoggedIn(session)) {
            return "redirect:/accounts/login";
        }
        Accounts admin = sessionService.getLoggedInUser(session);
        if (admin.getRole() != Role.ADMIN) {
            return "redirect:/user/profile";
        }

        Optional<Accounts> userOpt = accountsRepository.findById(id);
        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/admin/users";
        }

        Accounts userToDelete = userOpt.get();
        long adminCount = accountsRepository.countByRole(Role.ADMIN);
        
        log.info("Attempting to delete user: {}", userToDelete.getUsername());
        log.info("User role: {}, Admin count: {}", userToDelete.getRole(), adminCount);

        // Prevent deleting the last admin
        if (userToDelete.getRole() == Role.ADMIN && 
            adminCount <= 1) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete the last admin user");
            return "redirect:/admin/users";
        }

        // Delete from sipfriends table using msisdn as name
        if (userToDelete.getMisdsn() != null) {
            sipFriendRepository.deleteByName(userToDelete.getMisdsn());
        }

        accountsRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully");
        return "redirect:/admin/users";
    }

    @GetMapping("/calls")
    public String viewCalls(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/accounts/login";
        }
        model.addAttribute("username", username);
        model.addAttribute("calls", callLogRepository.findAll());
        return "admin/calls";
    }

    @GetMapping("/calls/{callId}")
    public String viewCallDetails(@PathVariable Long callId, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/accounts/login";
        }
        
        CallLog call = callLogRepository.findById(callId)
            .orElseThrow(() -> new RuntimeException("Call not found"));
            
        model.addAttribute("username", username);
        model.addAttribute("call", call);
        return "admin/view-call";
    }

    @GetMapping("/admin/transcripts")
    public String viewTranscripts(Model model, HttpSession session) {
        if (!sessionService.isUserLoggedIn(session)) {
            return "redirect:/accounts/login";
        }
        Accounts account = sessionService.getLoggedInUser(session);
        if (account.getRole() != Role.ADMIN) {
            return "redirect:/user/profile";
        }
        
        List<TranscriptDTO> transcripts = transcriptService.getAllTranscripts();
        model.addAttribute("username", account.getUsername());
        model.addAttribute("transcripts", transcripts);
        return "admin/transcripts";
    }

    @GetMapping("/admin/profile")
    public String viewAdminProfile(HttpSession session, Model model) {
        if (!sessionService.isUserLoggedIn(session)) {
            return "redirect:/accounts/login";
        }
        Accounts account = sessionService.getLoggedInUser(session);
        if (account.getRole() != Role.ADMIN) {
            return "redirect:/user/profile";
        }
        
        model.addAttribute("username", account.getUsername());
        model.addAttribute("fullName", account.getFullName());
        model.addAttribute("email", account.getEmail());
        model.addAttribute("misdsn", account.getMisdsn());
        model.addAttribute("role", account.getRole());
        
        return "admin/profile";
    }

    @GetMapping("/admin/call-details/{callId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCallDetails(@PathVariable Long callId, HttpSession session) {
        if (!sessionService.isUserLoggedIn(session)) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        Accounts account = sessionService.getLoggedInUser(session);
        if (account.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        Optional<CallLog> callLogOptional = callLogRepository.findById(callId);

        if (callLogOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CallLog call = callLogOptional.get();

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
