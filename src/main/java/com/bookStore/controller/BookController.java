package com.bookStore.controller;

import com.bookStore.entity.Book;
import com.bookStore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    // Get book by id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.findBookById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    // Update a book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook) {
        return bookService.findBookById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setBookStore(updatedBook.getBookStore());
                    Book savedBook = bookService.saveBook(book);
                    return ResponseEntity.ok(savedBook);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookService.findBookById(id).isPresent()) {
            bookService.deleteBookById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
