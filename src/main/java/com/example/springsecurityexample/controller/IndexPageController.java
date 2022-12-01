package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.model.SimpleModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPageController {

    @GetMapping({"", "/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("simple_model", new SimpleModel("Index page"));
        return "index";
    }
}
