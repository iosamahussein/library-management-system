package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.entity.BorrowingRecord;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
    
}
