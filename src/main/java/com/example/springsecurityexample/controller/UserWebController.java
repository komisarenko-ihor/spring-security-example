package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserWebController {

    private final UserRepository userRepository;

    @GetMapping("/register2fa")
    public String register2fa(Model model) {

        model.addAttribute("googleurl", "todo");

        return "user/register2fa";
    }

    @PostMapping("/register2fa")
    public String confirm2fa(@RequestParam Integer verifyCode) {

        //TODO: need to implement

        return "index";
    }
}
