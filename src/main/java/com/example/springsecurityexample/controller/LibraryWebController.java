package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.domain.Book;
import com.example.springsecurityexample.security.annotations.permissions.LibraryCreatePermission;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
public class LibraryWebController {

    @LibraryCreatePermission
    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("book", Book.builder().build());
        return "library/addBook";
    }

    @LibraryCreatePermission
    @PostMapping("/new")
    public String processCreationForm(Book book) {
        Book newBook = Book.builder()
                .name(book.getName())
                .build();

        // save book to repository

        return "redirect:/index";
    }
}
