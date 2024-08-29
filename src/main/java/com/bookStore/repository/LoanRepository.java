package com.bookStore.repository;

import com.bookStore.entity.Book;
import com.bookStore.entity.Loan;
import com.bookStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByBook(Book book);

    List<Loan> findByUser(User user);

    List<Loan> findByUserAndBook(User user, Book book);

    List<Loan> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Loan> findByBookAndEndDateAfter(Book book, LocalDate currentDate);

}
