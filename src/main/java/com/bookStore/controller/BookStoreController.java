package com.bookStore.controller;

import com.bookStore.entity.BookStore;
import com.bookStore.service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookstores")
public class BookStoreController {

    private final BookStoreService bookStoreService;

    @Autowired
    public BookStoreController(BookStoreService bookStoreService) {
        this.bookStoreService = bookStoreService;
    }

    // Get all book stores
    @GetMapping
    public List<BookStore> getAllBookStores() {
        return bookStoreService.findAllBookStores();
    }

    // Get book store by id
    @GetMapping("/{id}")
    public Optional<BookStore> getBookStoreById(@PathVariable Long id) {
        return bookStoreService.findBookStoreById(id);
    }

    // Create a new book store
    @PostMapping
    public BookStore createBookStore(@RequestBody BookStore bookStore) {
        return bookStoreService.saveBookStore(bookStore);
    }

    // Update a book store
    @PutMapping("/{id}")
    public BookStore updateBookStore(@PathVariable Long id, @RequestBody BookStore updatedBookStore) {
        return bookStoreService.findBookStoreById(id)
                .map (bookStore -> {
                    bookStore.setName(updatedBookStore.getName());
                    bookStore.setBooks(updatedBookStore.getBooks());
                    return bookStoreService.saveBookStore(bookStore);
                })
                .orElseGet(() -> {
                    updatedBookStore.setId(id);
                    return bookStoreService.saveBookStore(updatedBookStore);
                });
    }

    // Delete a book store
    @DeleteMapping("/{id}")
    public void deleteBookStore(@PathVariable Long id) {
        bookStoreService.deleteBookStoreById(id);
    }



}
