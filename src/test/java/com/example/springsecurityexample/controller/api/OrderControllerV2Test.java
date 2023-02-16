package com.example.springsecurityexample.controller.api;

import com.example.springsecurityexample.bootstrap.UserDataLoader;
import com.example.springsecurityexample.controller.BaseIT;
import com.example.springsecurityexample.domain.Client;
import com.example.springsecurityexample.domain.Order;
import com.example.springsecurityexample.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderControllerV2Test extends BaseIT {

    private static final String API_ROOT = "/api/v2/client/";

    @Autowired
    ClientRepository clientRepository;

    Client client1;

    Order orderByClient1;

    @BeforeEach
    public void initEach() {
        client1 = clientRepository.findAllByName(UserDataLoader.CLIENT_1).orElseThrow();

        orderByClient1 = client1.getOrders().stream().findFirst().orElseThrow();
    }

    @DisplayName("Get Order by ID")
    @Nested
    class GetOrderById {

        @Test
        void getOrderByIdNotAuth() throws Exception {
            mockMvc.perform(get(API_ROOT + "/orders/" + orderByClient1.getId()))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails("Admin1")
        @Test
        void getOrderByIdAdmin() throws Exception {
            mockMvc.perform(get(API_ROOT + "/orders/" + orderByClient1.getId()))
                    .andExpect(status().is2xxSuccessful());
        }

        @WithUserDetails(UserDataLoader.USER_1)
        @Test
        void getOrderByIdAuthClient() throws Exception {
            mockMvc.perform(get(API_ROOT + "/orders/" + orderByClient1.getId()))
                    .andExpect(status().is2xxSuccessful());
        }

        @WithUserDetails(UserDataLoader.USER_2)
        @Test
        void getOrderByIdWithWrongClient() throws Exception {
            mockMvc.perform(get(API_ROOT + "/orders/" + orderByClient1.getId()))
                    .andExpect(status().isNotFound());
        }
    }

    @DisplayName("List Orders")
    @Nested
    class ListOrders {

        @Test
        void listOrderByIdNotAuth() throws Exception {
            mockMvc.perform(get(API_ROOT + "/orders/"))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails("Admin1")
        @Test
        void listOrderByIdAdmin() throws Exception {
            mockMvc.perform(get(API_ROOT + "/orders/"))
                    .andExpect(status().is2xxSuccessful());
        }

        @WithUserDetails(UserDataLoader.USER_1)
        @Test
        void listOrderByIdAuthClient() throws Exception {
            mockMvc.perform(get(API_ROOT + "/orders/"))
                    .andExpect(status().is2xxSuccessful());
        }
    }
}
