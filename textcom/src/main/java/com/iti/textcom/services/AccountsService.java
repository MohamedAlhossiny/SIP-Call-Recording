package com.iti.textcom.services;

import com.iti.textcom.entity.Accounts;
import com.iti.textcom.entity.Role;
import com.iti.textcom.entity.SipFriend;
import com.iti.textcom.repository.AccountsRepository;
import com.iti.textcom.repository.SipFriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.iti.textcom.entity.TypeEnum;
import com.iti.textcom.entity.YesNoEnum;

@Service
public class AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private SipFriendRepository sipFriendRepository;

    @Transactional
    public Accounts registerUser(Accounts account) {
        // Validate that it's a user account
        // if (account.getRole() != Role.USER) {
        //     throw new IllegalArgumentException("Only user accounts can be registered");
        // }

        // Check if username exists
        if (accountsRepository.existsByUsername(account.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email exists
        if (account.getEmail() != null && accountsRepository.existsByEmail(account.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Check if MISDSN exists
        if (account.getMisdsn() != null && accountsRepository.existsByMisdsn(account.getMisdsn())) {
            throw new RuntimeException("MISDSN already registered");
        }

        String rawPassword = account.getPassword();
        String msisdn = account.getMisdsn();

        // Hash the password before saving to accounts table
        String hashedPassword = generateMd5Hash(msisdn + ":asterisk:" + rawPassword);
        account.setPassword(hashedPassword);

        Accounts savedAccount = accountsRepository.save(account);

        // Create and save SipFriend entry
        if (savedAccount.getMisdsn() != null) {
            SipFriend sipFriend = new SipFriend(
                savedAccount.getMisdsn(), // name
                savedAccount.getMisdsn(), // defaultuser
                "dynamic",                 // host
                TypeEnum.friend,                  // type
                "default",                 // context
                hashedPassword,                 // md5secret
                "rfc2833",                 // dtmfmode
                "force_rport,comedia",     // nat
                "ulaw,alaw",               // allow
                YesNoEnum.yes                      // dynamic
            );
            sipFriendRepository.save(sipFriend);
        }

        return savedAccount;
    }

    public Accounts login(String username, String password) {
        return accountsRepository.findByUsername(username)
            .filter(account -> {
                if (account.getMisdsn() == null) {
                    return false; // Cannot authenticate if MISDSN is null as it's part of the hash
                }
                String hashedLoginPassword = generateMd5Hash(account.getMisdsn() + ":asterisk:" + password);
                return account.getPassword().equals(hashedLoginPassword);
            })
            .orElse(null);
    }

    private String generateMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
} 