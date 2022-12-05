package com.example.springsecurityexample.controller;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncodingTests {

    static final String PASSWORD = "password1";

    @Test
    void ldapConversionForSecurityConfig() {
        PasswordEncoder ldap = new LdapShaPasswordEncoder();
        System.out.println(ldap.encode(PASSWORD));
        System.out.println(ldap.encode("password2"));
    }

    @Test
    void testLdap() {
        PasswordEncoder ldap = new LdapShaPasswordEncoder();

        String ldap1 = ldap.encode(PASSWORD);
        String ldap2 = ldap.encode(PASSWORD);

        System.out.println(ldap1);

        assertNotEquals(ldap1, ldap2);
        assertTrue(ldap.matches(PASSWORD, ldap1));
        assertTrue(ldap.matches(PASSWORD, ldap2));
    }

    @Test
    void testNoOp() {
        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();

        System.out.println(noOp.encode(PASSWORD));
    }

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
