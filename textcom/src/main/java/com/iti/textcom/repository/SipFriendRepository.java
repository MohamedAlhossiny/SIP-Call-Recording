package com.iti.textcom.repository;

import com.iti.textcom.entity.SipFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SipFriendRepository extends JpaRepository<SipFriend, Integer> {
    void deleteByName(String name);
    // You can add custom query methods here if needed
} 