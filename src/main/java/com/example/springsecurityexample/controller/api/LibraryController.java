package com.example.springsecurityexample.controller.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @PostMapping("/")
    public void addBook() {
        // adding book
    }

    @PutMapping("/{id}")
    public String updateBook() {
        return "Updated book";
    }

    @GetMapping("/{id}")
    public String readBook() {
        return "Book";
    }

    @DeleteMapping("/{id}")
    public void deleteBook() {
        // deleting book
    }
}
