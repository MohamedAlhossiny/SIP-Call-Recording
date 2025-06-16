package com.iti.textcom.controller;

import com.iti.textcom.entity.Accounts;
import com.iti.textcom.entity.Role;
import com.iti.textcom.services.AccountsService;
import com.iti.textcom.services.SessionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpSession session) {
        if (sessionService.isUserLoggedIn(session)) {
            Accounts account = sessionService.getLoggedInUser(session);
            if (account.getRole() == Role.ADMIN) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/profile";
            }
        }
        model.addAttribute("account", new Accounts());
        return "accounts/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Accounts account, RedirectAttributes redirectAttributes) {
        try {
            account.setRole(Role.USER); // Force role to be user
            accountsService.registerUser(account);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
            return "redirect:/accounts/login";
        } catch (ConstraintViolationException ex) {
            StringBuilder errorMsg = new StringBuilder();
            ex.getConstraintViolations().forEach(v -> errorMsg.append(v.getMessage()).append("<br/>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMsg.toString());
            return "redirect:/accounts/register";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/accounts/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (sessionService.isUserLoggedIn(session)) {
            Accounts account = sessionService.getLoggedInUser(session);
            if (account.getRole() == Role.ADMIN) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/profile";
            }
        }
        return "accounts/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        Accounts account = accountsService.login(username, password);
        if (account != null) {
            sessionService.createUserSession(session, account);
            if (account.getRole() == Role.ADMIN) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/profile";
            }
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
        return "redirect:/accounts/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        sessionService.invalidateSession(session);
        return "redirect:/accounts/login";
    }
}