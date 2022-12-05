package com.example.springsecurityexample.controller;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PasswordEncodingTests {

    static final String PASSWORD = "password";

    @Test
    void hashingExample() {
        String hash1 = DigestUtils.md5DigestAsHex(PASSWORD.getBytes());
        String hash2 = DigestUtils.md5DigestAsHex(PASSWORD.getBytes());

        String salted = PASSWORD + "ThisIsMySaltValue";

        String saltedHash1 = DigestUtils.md5DigestAsHex(salted.getBytes());
        String saltedHash2 = DigestUtils.md5DigestAsHex(salted.getBytes());

        assertEquals(hash1, hash2);
        assertEquals(saltedHash1, saltedHash2);
    }
}
