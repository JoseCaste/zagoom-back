package com.core.repository;

import com.core.entity.EmailRecoveryToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRecoveryTokenRepository extends JpaRepository<EmailRecoveryToken, Long> {
    Optional<EmailRecoveryToken> findByToken(int token);
}
