package com.example.springprac2jwt.repository;

import com.example.springprac2jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository  extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUsername(String username);
}
