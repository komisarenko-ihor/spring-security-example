package com.example.springsecurityexample.controller.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @PreAuthorize("hasAuthority('library.create')")
    @PostMapping("/")
    public void addBook() {
        // adding book
    }

    @PreAuthorize("hasAuthority('library.read')")
    @GetMapping("/{id}")
    public String readBook() {
        return "Book";
    }

    @PreAuthorize("hasAuthority('library.update')")
    @PutMapping("/{id}")
    public String updateBook() {
        return "Updated book";
    }

    @PreAuthorize("hasAuthority('library.delete')")
    @DeleteMapping("/{id}")
    public void deleteBook() {
        // deleting book
    }
}
