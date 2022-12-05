package com.example.springsecurityexample.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class IndexPageControllerIT extends BaseIT {

    @Test
    void getIndexPage1() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @WithMockUser("spring")
    @Test
    void getIndexPage() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("simple_model"));
    }

    @Test
    void getIndexPageWithHttpBasic() throws Exception {
        mockMvc.perform(get("/index").with(httpBasic("user1", "password1")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("simple_model"));
    }

    @Test
    void getIndexPageWithHttpBasic2() throws Exception {
        mockMvc.perform(get("/index").with(httpBasic("user2", "password2")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("simple_model"));
    }
}
