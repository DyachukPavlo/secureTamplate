package com.securetemplate.repository;

import com.securetemplate.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}