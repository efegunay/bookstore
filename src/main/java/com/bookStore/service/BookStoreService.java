package com.bookStore.service;

import com.bookStore.entity.BookStore;
import com.bookStore.repository.BookStoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookStoreService {

    private final BookStoreRepository bookStoreRepository;

    @Autowired
    public BookStoreService(BookStoreRepository bookStoreRepository) {
        this.bookStoreRepository = bookStoreRepository;
    }

    public BookStore saveBookStore(BookStore bookStore) {
        return bookStoreRepository.save(bookStore);
    }

    public Optional<BookStore> findBookStoreById(Long id) {
        return bookStoreRepository.findById(id);
    }

    public List<BookStore> findBookStoreByName(String name) {
        return bookStoreRepository.findByName(name);
    }

    public List<BookStore> findAllBookStores() {
        return bookStoreRepository.findAll();
    }

    @Transactional
    public void deleteBookStoreById(Long id) {
        if (!bookStoreRepository.existsById(id)) {
            throw new IllegalArgumentException("BookStore with id " + id + " does not exist.");
        }
        bookStoreRepository.deleteById(id);
    }
}
