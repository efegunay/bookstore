package com.bookStore.repository;

import com.bookStore.entity.BookStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookStoreRepository extends JpaRepository<BookStore, Long> {

    List<BookStore> findByName(String name);

}
