package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
    public boolean existsByEmail(String email);
}
