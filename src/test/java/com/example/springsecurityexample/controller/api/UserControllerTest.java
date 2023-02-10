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
    void getCustomerWithCustomerRole() throws Exception {
        mockMvc.perform(get("/api/customer/1")
                .with(httpBasic("Customer1", "password3")))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerWithNotCustomerRole() throws Exception {
        mockMvc.perform(get("/api/customer/1")
                .with(httpBasic("Admin1", "password1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void getCustomerWithNoAuth() throws Exception {
        mockMvc.perform(get("/api/customer/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteSomeWithAdminRole() throws Exception {
        mockMvc.perform(delete("/api/user/some")
                        .with(httpBasic("Admin1", "password1")))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSomeWithUserRole() throws Exception {
        mockMvc.perform(delete("/api/user/some")
                        .with(httpBasic("User1", "password2")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteSomeWithCustomerRole() throws Exception {
        mockMvc.perform(delete("/api/user/some")
                        .with(httpBasic("Customer1", "password3")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteSomeWithNoAuth() throws Exception {
        mockMvc.perform(delete("/api/user/some"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getSomeValueWithNoAuth() throws Exception {
        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getSomeValueWithAuth() throws Exception {
        mockMvc.perform(get("/api/user").with(httpBasic("Admin1", "password1")))
                .andExpect(status().isOk());
    }

    @Test
    void getSomeValueWithAuth2() throws Exception {
        mockMvc.perform(get("/api/user/some").with(httpBasic("User1", "password2")))
                .andExpect(status().isOk());
    }

    @Test
    void getSomeValueUPC() throws Exception {
        mockMvc.perform(get("/api/user/upc/000"))
                .andExpect(status().isOk());
    }
}