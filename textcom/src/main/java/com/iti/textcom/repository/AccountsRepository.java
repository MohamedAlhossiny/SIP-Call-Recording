package com.iti.textcom.repository;

import com.iti.textcom.entity.Accounts;
import com.iti.textcom.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    Optional<Accounts> findByMisdsn(String misdsn);
    Optional<Accounts> findByUsername(String username);
    Optional<Accounts> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByMisdsn(String misdsn);
    @Query("SELECT COUNT(a) FROM Accounts a WHERE a.role = 'user'")
    long countUsers();
    List<Accounts> findTop3ByOrderByIdDesc();
    @Query("SELECT COUNT(a) FROM Accounts a WHERE a.role = :role")
    long countByRole(Role role);
    @Query("SELECT a FROM Accounts a WHERE a.role != :role ORDER BY a.id DESC")
    List<Accounts> findByRoleNotOrderByIdDesc(Role role);
}