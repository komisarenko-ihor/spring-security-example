package com.example.springsecurityexample.controller.api;

import com.example.springsecurityexample.bootstrap.UserDataLoader;
import com.example.springsecurityexample.controller.BaseIT;
import com.example.springsecurityexample.domain.Client;
import com.example.springsecurityexample.domain.Order;
import com.example.springsecurityexample.repositories.ClientRepository;
import com.example.springsecurityexample.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderControllerTest extends BaseIT {

    private static final String API_ROOT = "/api/client/";

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    OrderRepository orderRepository;

    Client client1;
    Client client2;
    Client client3;

    Order orderByClient1;

    @BeforeEach
    public void initEach() {
        client1 = clientRepository.findAllByName(UserDataLoader.CLIENT_1).orElseThrow();
        client2 = clientRepository.findAllByName(UserDataLoader.CLIENT_2).orElseThrow();
        client3 = clientRepository.findAllByName(UserDataLoader.CLIENT_3).orElseThrow();

        orderByClient1 = client1.getOrders().stream().findFirst().orElseThrow();
    }

    @DisplayName("Create Order")
    @Nested
    class CreateOrderTest {

        @Test
        void createOrderNotAuth() throws Exception {
            mockMvc.perform(post(API_ROOT + client1.getId() + "/orders"))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails("Admin1")
        @Test
        void createOrderAdmin() throws Exception {
            mockMvc.perform(post(API_ROOT + client1.getId() + "/orders"))
                    .andExpect(status().isCreated());
        }

        @WithUserDetails(UserDataLoader.USER_1)
        @Test
        void createOrderAuthClient() throws Exception {
            mockMvc.perform(post(API_ROOT + client1.getId() + "/orders"))
                    .andExpect(status().isCreated());
        }

        @WithUserDetails(UserDataLoader.USER_2)
        @Test
        void createdOrderNOTAuthClient() throws Exception {
            mockMvc.perform(post(API_ROOT + client1.getId() + "/orders"))
                    .andExpect(status().isForbidden());
        }
    }

    @DisplayName("List Orders")
    @Nested
    class ListOrders {

        @Test
        void listOrdersNotAuth() throws Exception {
            mockMvc.perform(get(API_ROOT + client1.getId() + "/orders"))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails("Admin1")
        @Test
        void listOrdersWithAdmin() throws Exception {
            mockMvc.perform(get(API_ROOT + client1.getId() + "/orders"))
                    .andExpect(status().is2xxSuccessful());
        }

        @WithUserDetails(UserDataLoader.USER_1)
        @Test
        void listOrdersWithAuthClient() throws Exception {
            mockMvc.perform(get(API_ROOT + client1.getId() + "/orders"))
                    .andExpect(status().is2xxSuccessful());
        }

        @WithUserDetails(UserDataLoader.USER_2)
        @Test
        void listOrdersWithNotAuthClient() throws Exception {
            mockMvc.perform(get(API_ROOT + client1.getId() + "/orders"))
                    .andExpect(status().isForbidden());
        }
    }

    @DisplayName("Get Order by ID")
    @Nested
    class GetOrderById {

        @Test
        void getOrderByIdNotAuth() throws Exception {
            mockMvc.perform(get(API_ROOT + client1.getId() + "/orders/" + orderByClient1.getId()))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails("Admin1")
        @Test
        void getOrderByIdAdmin() throws Exception {
            mockMvc.perform(get(API_ROOT + client1.getId() + "/orders/" + orderByClient1.getId()))
                    .andExpect(status().is2xxSuccessful());
        }

        @WithUserDetails(UserDataLoader.USER_1)
        @Test
        void getOrderByIdAuthClient() throws Exception {
            mockMvc.perform(get(API_ROOT + client1.getId() + "/orders/" + orderByClient1.getId()))
                    .andExpect(status().is2xxSuccessful());
        }

        @WithUserDetails(UserDataLoader.USER_2)
        @Test
        void getOrderByIdNotAuthClient() throws Exception {
            mockMvc.perform(get(API_ROOT + client1.getId() + "/orders/" + orderByClient1.getId()))
                    .andExpect(status().isForbidden());
        }
    }

    @DisplayName("Delete order")
    @Nested
    class DeleteOrder {

        @Test
        void deleteOrderByIdNotAuth() throws Exception {
            mockMvc.perform(delete(API_ROOT + client1.getId() + "/orders/" + orderByClient1.getId()))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails("Admin1")
        @Test
        void deleteOrderByIdAdmin() throws Exception {
            // given
            Order orderTemp = orderRepository.save(Order.builder().client(client1).id(UUID.randomUUID()).build());
            //when then
            mockMvc.perform(delete(API_ROOT + client1.getId() + "/orders/" + orderTemp.getId()))
                    .andExpect(status().is2xxSuccessful());
        }

        @WithUserDetails(UserDataLoader.USER_1)
        @Test
        void deleteOrderByIdAuthClient() throws Exception {
            // given
            Order orderTemp = orderRepository.save(Order.builder().client(client1).id(UUID.randomUUID()).build());
            //when then
            mockMvc.perform(delete(API_ROOT + client1.getId() + "/orders/" + orderTemp.getId()))
                    .andExpect(status().is2xxSuccessful());
        }

        @WithUserDetails(UserDataLoader.USER_2)
        @Test
        void deleteOrderByIdNotAuthClient() throws Exception {
            mockMvc.perform(delete(API_ROOT + client1.getId() + "/orders/" + orderByClient1.getId()))
                    .andExpect(status().isForbidden());
        }
    }
}
