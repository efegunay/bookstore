package com.bookStore.controller;

import com.bookStore.entity.BookStore;
import com.bookStore.service.BookStoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<BookStore>> getAllBookStores() {
        return ResponseEntity.ok(bookStoreService.findAllBookStores());
    }

    // Get book store by id
    @GetMapping("/{id}")
    public ResponseEntity<BookStore> getBookStoreById(@PathVariable Long id) {
        return bookStoreService.findBookStoreById(id)
                .map(bookStore -> ResponseEntity.ok(bookStore))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create a new book store
    @PostMapping
    public ResponseEntity<BookStore> createBookStore(@Valid @RequestBody BookStore bookStore) {
        BookStore savedBookStore = bookStoreService.saveBookStore(bookStore);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookStore);
    }

    // Update a book store
    @PutMapping("/{id}")
    public ResponseEntity<BookStore> updateBookStore(@PathVariable Long id, @Valid @RequestBody BookStore updatedBookStore) {
        return bookStoreService.findBookStoreById(id)
                .map(bookStore -> {
                    bookStore.setName(updatedBookStore.getName());
                    bookStore.setBooks(updatedBookStore.getBooks());
                    BookStore savedBookStore = bookStoreService.saveBookStore(bookStore);
                    return ResponseEntity.ok(savedBookStore);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Delete a book store
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookStore(@PathVariable Long id) {
        if (bookStoreService.findBookStoreById(id).isPresent()) {
            bookStoreService.deleteBookStoreById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

