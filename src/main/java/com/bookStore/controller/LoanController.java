package com.bookStore.controller;

import com.bookStore.entity.Loan;
import com.bookStore.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public List<Loan> getAllLoans() {
        return loanService.findAll();
    }

    // Get loan by id
    @GetMapping("/{id}")
    public Optional<Loan> getLoanById(@PathVariable Long id) {
        return loanService.findById(id);
    }

    // Create a new loan
    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        try {
            return loanService.saveLoan(loan);
        } catch (RuntimeException e) {
            throw new RuntimeException("Book is already loaned");
        }
    }

    // Update a loan
    @PutMapping("/{id}")
    public Loan updateLoan(@PathVariable Long id, @RequestBody Loan updatedLoan) {
        return loanService.findById(id)
                .map(loan -> {
                    loan.setStartDate(updatedLoan.getStartDate());
                    loan.setEndDate(updatedLoan.getEndDate());
                    loan.setUser(updatedLoan.getUser());
                    loan.setBook(updatedLoan.getBook());
                    return loanService.saveLoan(loan);
                })
                .orElseGet(() -> {
                    updatedLoan.setId(id);
                    return loanService.saveLoan(updatedLoan);
                });
    }

    // Delete a loan
    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteById(id);
    }
}
