package com.iti.textcom.services;

import com.iti.textcom.entity.Accounts;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    
    private static final String USER_SESSION_KEY = "userAccount";
    
    public void createUserSession(HttpSession session, Accounts account) {
        session.setAttribute(USER_SESSION_KEY, account);
    }
    
    public Accounts getLoggedInUser(HttpSession session) {
        return (Accounts) session.getAttribute(USER_SESSION_KEY);
    }
    
    public boolean isUserLoggedIn(HttpSession session) {
        return getLoggedInUser(session) != null;
    }
    
    public void invalidateSession(HttpSession session) {
        session.invalidate();
    }
} 