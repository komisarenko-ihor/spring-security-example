package com.example.springsecurityexample.controller.api;

import com.example.springsecurityexample.security.annotations.permissions.LibraryCreatePermission;
import com.example.springsecurityexample.security.annotations.permissions.LibraryDeletePermission;
import com.example.springsecurityexample.security.annotations.permissions.LibraryReadPermission;
import com.example.springsecurityexample.security.annotations.permissions.LibraryUpdatePermission;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @LibraryCreatePermission
    @PostMapping("/")
    public void addBook() {
        // adding book
    }

    @LibraryReadPermission
    @GetMapping("/{id}")
    public String readBook() {
        return "Book";
    }

    @LibraryUpdatePermission
    @PutMapping("/{id}")
    public String updateBook() {
        return "Updated book";
    }

    @LibraryDeletePermission
    @DeleteMapping("/{id}")
    public void deleteBook() {
        // deleting book
    }
}
