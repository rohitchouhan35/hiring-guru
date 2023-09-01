package com.rohitchouhan35.hiringmadeeasy.repository;

import com.rohitchouhan35.hiringmadeeasy.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

}
