package com.bookStore.service;

import com.bookStore.entity.Book;
import com.bookStore.entity.Loan;
import com.bookStore.entity.User;
import com.bookStore.repository.BookRepository;
import com.bookStore.repository.LoanRepository;
import com.bookStore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {

        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public Loan saveLoan(Loan loan) {

        Book book = loan.getBook();
        LocalDate today = LocalDate.now();

        List<Loan> activeLoans = loanRepository.findByBookAndEndDateAfter(book, today);

        if (!activeLoans.isEmpty()) {
            throw new RuntimeException("Book is already loaned");
        }
        return loanRepository.save(loan);
    }

    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id);
    }

    public List<Loan> findByBook(Book book) {
        return loanRepository.findByBook(book);
    }

    public List<Loan> findByUser(User user) {
        return loanRepository.findByUser(user);
    }

    public List<Loan> findByUserAndBook(User user, Book book) {
        return loanRepository.findByUserAndBook(user, book);
    }

    public List<Loan> findByStartDateBetween(LocalDate startDate, LocalDate endDate) {
        return loanRepository.findByStartDateBetween(startDate, endDate);
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public void deleteById(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new IllegalArgumentException("Loan with id " + id + " does not exist.");
        }
        loanRepository.deleteById(id);
    }
}
