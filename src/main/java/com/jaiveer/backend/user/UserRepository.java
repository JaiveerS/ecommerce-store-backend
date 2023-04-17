package com.jaiveer.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailIgnoreCase(String email);
    
    boolean existsByEmailIgnoreCase(String email);
}
