package com.example.springsecurityexample.controller.api;

import com.example.springsecurityexample.controller.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest extends BaseIT {

    @Test
    void deleteSome() throws Exception {
        mockMvc.perform(delete("/api/user/some")
                .header("Api-Key", "user1").header("Api-Secret", "password1"))
                .andExpect(status().isOk());
    }

    @Test
    void getSomeValueWithNoAuth() throws Exception {
        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getSomeValueWithAuth() throws Exception {
        mockMvc.perform(get("/api/user").with(httpBasic("user1", "password1")))
                .andExpect(status().isOk());
    }

    @Test
    void getSomeValueWithAuth2() throws Exception {
        mockMvc.perform(get("/api/user/some").with(httpBasic("user2", "password2")))
                .andExpect(status().isOk());
    }

    @Test
    void getSomeValueUPC() throws Exception {
        mockMvc.perform(get("/api/user/upc/000"))
                .andExpect(status().isOk());
    }
}