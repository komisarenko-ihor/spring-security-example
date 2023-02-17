package com.example.springsecurityexample.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class LibraryWebControllerTest extends BaseIT {

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/library/new")
                        .with(httpBasic("Admin1", "password1"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("library/addBook"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    void processCreationForm() throws Exception {
        mockMvc.perform(post("/library/new")
                        .with(httpBasic("Admin1", "password1"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }
}
