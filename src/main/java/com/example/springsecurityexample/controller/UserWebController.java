package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.domain.security.User;
import com.example.springsecurityexample.repositories.security.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final GoogleAuthenticator googleAuthenticator;

    @GetMapping("/register2fa")
    public String register2fa(Model model) {

        User user = getUser();

        String url = GoogleAuthenticatorQRGenerator.getOtpAuthURL("SpringSecurityExample", user.getUsername(),
                googleAuthenticator.createCredentials(user.getUsername()));

        log.debug("Google QR URL: " + url);

        model.addAttribute("googleurl", url);

        return "user/register2fa";
    }

    @PostMapping("/register2fa")
    public String confirm2fa(@RequestParam Integer verifyCode) {

        User user = getUser();

        log.debug("Entered Code is: " + verifyCode);

        String page;

        if (googleAuthenticator.authorizeUser(user.getUsername(), verifyCode)) {
            User saverUser = userRepository.findById(user.getId()).orElseThrow();
            saverUser.setUseGoogle2Fa(true);
            userRepository.save(saverUser);

            page = "index";
        } else {
            page = "user/register2fa";
        }

        return page;
    }

    @GetMapping("/verify2fa")
    public String verify2fa() {
        return "user/verify2fa";
    }

    @PostMapping("/verify2fa")
    public String verifyPost2fa(@RequestParam Integer verifyCode) {

        User user = getUser();

        String page;

        if (googleAuthenticator.authorizeUser(user.getUsername(), verifyCode)) {
            ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setUseGoogle2Fa(false);
            page = "index";
        } else {
            page = "user/verify2fa";
        }

        return page;
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
