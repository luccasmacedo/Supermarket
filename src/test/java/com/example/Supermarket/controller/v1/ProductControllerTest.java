package com.example.Supermarket.controller.v1;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import com.example.Supermarket.controllers.v1.ProductController;
import com.example.Supermarket.exception.ProductNotFoundException;
import com.example.Supermarket.model.Product;
import com.example.Supermarket.service.ProductService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ContextConfiguration
@WebMvcTest(ProductController.class)
@WithMockUser
@AutoConfigureMockMvc
public class ProductControllerTest {

        @MockBean
        private  ProductService productService;

        @MockBean
        private ConnectionFactory connectionFactory;

        @Autowired
        protected MockMvc mockMvc;

        private static Product mockProduct1;
        private static Product mockProduct2;
        private static Product mockProduct3;

        @BeforeAll
        public static void setup() {

                mockProduct1 = new Product();
                mockProduct1.setProductName("heyday Bluetooth Round Speaker with Loop - River Green");
                mockProduct1.setStockAmount(10);
                mockProduct1.setUnitPrice(9.99f);

                mockProduct2 = new Product();
                mockProduct2.setProductName("3pk Paint-Your-Own Wood Popsicles Kit - Mondo Llam");
                mockProduct2.setStockAmount(8);
                mockProduct2.setUnitPrice(7.99f);

                mockProduct3 = new Product();
                mockProduct3.setProductName("Farberware 12 x 16 Nonstick Roaster with Rack");
                mockProduct3.setStockAmount(6);
                mockProduct3.setUnitPrice(5.99f);
        }

        @Test
        @DisplayName("Should retrieve all products")
        @WithMockUser(authorities = "ADMIN")
        public void retrieveAllProducts() throws Exception {

                ArrayList<Product> listOfProducts = new ArrayList<Product>();
                listOfProducts.add(mockProduct1);
                listOfProducts.add(mockProduct2);
                listOfProducts.add(mockProduct3);

                when(productService.findAll()).thenReturn(listOfProducts);

                this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products"))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName")
                                                .value("heyday Bluetooth Round Speaker with Loop - River Green"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productName")
                                                .value("3pk Paint-Your-Own Wood Popsicles Kit - Mondo Llam"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[2].productName")
                                                .value("Farberware 12 x 16 Nonstick Roaster with Rack"));
        }

        @Test
        @DisplayName("Should retrieve product by id")
        @WithMockUser(authorities = "CASHIER")
        public void retrieveProductById() throws Exception {

                String expectResponse = "{\"id\":0,\"productName\":\"heyday Bluetooth Round Speaker with Loop - River Green\",\"unitPrice\":9.99,\"stockAmount\":10}";

                when(productService.findById(0L)).thenReturn(mockProduct1);

                RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/products/0")
                                .accept(MediaType.APPLICATION_JSON);

                MvcResult result = mockMvc.perform(requestBuilder).andReturn();

                Assertions.assertEquals(expectResponse, result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Should respond NOT_FOUND when retrieveng product that dont exists")
        @WithMockUser(authorities = "CASHIER")
        public void shouldNotFindProduct() throws Exception {
                when(productService.findById(0L)).thenThrow(ProductNotFoundException.class);
                this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/0"))
                                .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        @DisplayName("Should save product")
        @WithMockUser(authorities = "ADMIN")
        public void shouldSaveProduct() throws Exception {
                when(productService.save(mockProduct1)).thenReturn(mockProduct1);
                this.mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"productName\": \"heyday Bluetooth Round Speaker with Loop - River Green\", \"stockAmount\":10, \"unitPrice\":9.99}"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("Should Not Allow Cashier to save product")
        @WithMockUser(authorities = "CASHIER")
        public void shouldNotAllowCashierToSaveProduct() throws Exception {
                when(productService.save(mockProduct1)).thenReturn(mockProduct1);
                this.mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"productName\": \"heyday Bluetooth Round Speaker with Loop - River Green\", \"stockAmount\":10, \"unitPrice\":9.99}"))
                                .andExpect(MockMvcResultMatchers.status().isForbidden());
        }


        @Test
        @DisplayName("Should delete Product by id")
        @WithMockUser(authorities = "ADMIN")
        public void shouldDeleteProductById() throws Exception {
                // when(productService.deleteProduct(0L)).thenReturn(mockProduct1);
                this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/0"))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Product deleted"));
        }
}