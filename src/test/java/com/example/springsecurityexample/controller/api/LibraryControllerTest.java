package com.example.springsecurityexample.controller.api;

import com.example.springsecurityexample.controller.BaseIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class LibraryControllerTest extends BaseIT {

    @Nested
    @DisplayName("Create Book")
    class CreateBook {

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("com.example.springsecurityexample.controller.api.LibraryControllerTest#getAdminCustomer")
        void createBookWithAuth(String user, String pwd) throws Exception {
            mockMvc.perform(post("/api/library/")
                    .with(httpBasic(user, pwd)))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void createBookWithNoAuth() throws Exception {
            mockMvc.perform(post("/api/library/"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void createBookWithWrongAuth() throws Exception {
            mockMvc.perform(post("/api/library/")
                    .with(httpBasic("User1", "password2")))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Read Book")
    class ReadBook {

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("com.example.springsecurityexample.controller.api.LibraryControllerTest#getReadBookCustomers")
        void readBookWithAuth(String user, String pwd) throws Exception {
            mockMvc.perform(get("/api/library/1")
                    .with(httpBasic(user, pwd)))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void readBookWithNoAuth() throws Exception {
            mockMvc.perform(get("/api/library/1"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void readBookWithWrongAuth() throws Exception {
            mockMvc.perform(get("/api/library/1")
                    .with(httpBasic("User1", "password2")))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Update Book")
    class UpdateBook {

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("com.example.springsecurityexample.controller.api.LibraryControllerTest#getAdminCustomer")
        void createBookWithAuth(String user, String pwd) throws Exception {
            mockMvc.perform(put("/api/library/1")
                            .with(httpBasic(user, pwd)))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void createBookWithNoAuth() throws Exception {
            mockMvc.perform(put("/api/library/1"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void createBookWithWrongAuth() throws Exception {
            mockMvc.perform(put("/api/library/1")
                            .with(httpBasic("User1", "password2")))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Delete Book")
    class DeleteBook {

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("com.example.springsecurityexample.controller.api.LibraryControllerTest#getAdminCustomer")
        void createBookWithAuth(String user, String pwd) throws Exception {
            mockMvc.perform(delete("/api/library/1")
                            .with(httpBasic(user, pwd)))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void createBookWithNoAuth() throws Exception {
            mockMvc.perform(delete("/api/library/1"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void createBookWithWrongAuth() throws Exception {
            mockMvc.perform(delete("/api/library/1")
                            .with(httpBasic("User1", "password2")))
                    .andExpect(status().isForbidden());
        }
    }
}
