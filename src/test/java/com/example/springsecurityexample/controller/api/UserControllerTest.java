package com.example.springsecurityexample.controller.api;

import com.example.springsecurityexample.controller.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest extends BaseIT {

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
}