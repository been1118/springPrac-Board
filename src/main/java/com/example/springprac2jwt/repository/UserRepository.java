package com.example.springprac2jwt.repository;

import com.example.springprac2jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); //중복유저 확인용
}
