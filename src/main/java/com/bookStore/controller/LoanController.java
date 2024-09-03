package com.bookStore.controller;

import com.bookStore.entity.Loan;
import com.bookStore.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // Get all loans
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.findAll());
    }

    // Get loan by id
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        return loanService.findById(id)
                .map(loan -> ResponseEntity.ok(loan))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create a new loan
    @PostMapping
    public ResponseEntity<Loan> createLoan(@Valid @RequestBody Loan loan) {
        try {
            Loan savedLoan = loanService.saveLoan(loan);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLoan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    // Update a loan
    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @Valid @RequestBody Loan updatedLoan) {
        return loanService.findById(id)
                .map(loan -> {
                    loan.setStartDate(updatedLoan.getStartDate());
                    loan.setEndDate(updatedLoan.getEndDate());
                    loan.setUser(updatedLoan.getUser());
                    loan.setBook(updatedLoan.getBook());
                    Loan savedLoan = loanService.saveLoan(loan);
                    return ResponseEntity.ok(savedLoan);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Delete a loan
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        if (loanService.findById(id).isPresent()) {
            loanService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

