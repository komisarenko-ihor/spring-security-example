package com.example.springsecurityexample.controller.api;

import com.example.springsecurityexample.controller.BaseIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest extends BaseIT {

    @DisplayName("Method Security")
    @Nested
    class MethodSecurity {

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("com.example.springsecurityexample.controller.api.UserControllerTest#getAdminCustomer")
        void getListCustomersWithAuth(String user, String pwd) throws Exception {
            mockMvc.perform(get("/api/customer/")
                            .with(httpBasic(user, pwd)))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void getListCustomersNoAuth() throws Exception {
            mockMvc.perform(get("/api/customer/"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void getListCustomersWrongAuth() throws Exception {
            mockMvc.perform(get("/api/customer/")
                            .with(httpBasic("User1", "password2")))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Get Customer")
    class GetCustomer {

        @Test
        void getCustomerWithCustomerRole() throws Exception {
            mockMvc.perform(get("/api/customer/1")
                            .with(httpBasic("Customer1", "password3")))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void getCustomerWithNotCustomerRole() throws Exception {
            mockMvc.perform(get("/api/customer/1")
                            .with(httpBasic("Admin1", "password1")))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void getCustomerWithNoAuth() throws Exception {
            mockMvc.perform(get("/api/customer/1"))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("Delete User")
    class DeleteUser {

        @Test
        void deleteSomeWithAdminRole () throws Exception {
            mockMvc.perform(delete("/api/user/some")
                            .with(httpBasic("Admin1", "password1")))
                    .andExpect(status().isOk());
        }

        @Test
        void deleteSomeWithUserRole () throws Exception {
            mockMvc.perform(delete("/api/user/some")
                            .with(httpBasic("User1", "password2")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteSomeWithCustomerRole () throws Exception {
            mockMvc.perform(delete("/api/user/some")
                            .with(httpBasic("Customer1", "password3")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteSomeWithNoAuth () throws Exception {
            mockMvc.perform(delete("/api/user/some"))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("Get User")
    class GetUser {

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
}