package com.edu_assess.edu_assess.backend.repository;

import com.edu_assess.edu_assess.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}