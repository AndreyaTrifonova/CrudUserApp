package com.example.CrudUserApp.repository;

import com.example.CrudUserApp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String first, String last, Pageable pageable);
}
