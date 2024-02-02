package com.jrs296.ems.EndToEndTests

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeLoginInputDTO;
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeRegisterInputDTO;
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class AuthSystemTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void welcomeTest() throws Exception {
        assert (restTemplate.getForObject("http://localhost:" + port + "/api/auth/test",
                String.class)).contains("Welcome this endpoint is not secure");
    }

    @Test
    void registerUser() throws Exception {
        EmployeeRegisterInputDTO inputDTO = new EmployeeRegisterInputDTO();
        inputDTO.setEmployeeName("Test User");
        inputDTO.setUserName("testuser");
        inputDTO.setEmployeeEmail("testuser@example.com");
        inputDTO.setPassword("12345678")

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:" + port + "/api/auth/register")
                .content(asJsonString(inputDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User Added Successfully"));
    }

    @Test
    void testLoginAsAdmin() throws Exception {
        EmployeeLoginInputDTO loginInputDTO = new EmployeeLoginInputDTO();
        loginInputDTO.setUsername("admin");
        loginInputDTO.setPassword("admin123");

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:" + port + "/api/auth/login")
                .content(asJsonString(loginInputDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn().toString();
    }

    @Test
    void testLoginAsUser() throws Exception {
        registerUser()

        EmployeeLoginInputDTO loginInputDTO = new EmployeeLoginInputDTO();
        loginInputDTO.setUsername("testuser");
        loginInputDTO.setPassword("12345678");

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:" + port + "/api/auth/login")
                .content(asJsonString(loginInputDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Test
    void testInvalidLoginAttempt() throws Exception {
        EmployeeLoginInputDTO loginInputDTO = new EmployeeLoginInputDTO();
        loginInputDTO.setUsername("243234234");
        loginInputDTO.setPassword("12345678");

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:" + port + "/api/auth/login")
                .content(asJsonString(loginInputDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.content().string("{\"errors\":[\"Bad credentials\"]}"));
    }

    @org.junit.jupiter.api.Nested
    class testAuthorities {
        private String adminToken;

        @BeforeEach
        void setupAuthToken() throws Exception {
            EmployeeLoginInputDTO loginInputDTO = new EmployeeLoginInputDTO();
            loginInputDTO.setUsername("admin");
            loginInputDTO.setPassword("admin123");

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:" + port + "/api/auth/login")
                    .content(asJsonString(loginInputDTO))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            // Extract token from response content
            adminToken = result.getResponse().getContentAsString();
        }

        @Test
        void adminAccessUserProfile_Success() {
            mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:" + port + "/api/auth/user/userProfile")
                    .header("Authorization", "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("Welcome to User Profile"));
        }

        @Test
        void adminAccessManagerProfile_Success() {
            mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:" + port + "/api/auth/manager/managerProfile")
                    .header("Authorization", "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("Welcome to Manager Profile"));
        }

        @Test
        void adminAccessAdminProfile_Success() {
            mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:" + port + "/api/auth/admin/adminProfile")
                    .header("Authorization", "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("Welcome to Admin Profile"));
        }
    }

    @org.junit.jupiter.api.Nested
    class testUserAuthorities {
        private String userToken;

        @BeforeEach
        void setupAuthToken() throws Exception {
            registerUser()

            EmployeeLoginInputDTO loginInputDTO = new EmployeeLoginInputDTO();
            loginInputDTO.setUsername("testuser");
            loginInputDTO.setPassword("12345678");

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:" + port + "/api/auth/login")
                    .content(asJsonString(loginInputDTO))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            // Extract token from response content
            userToken = result.getResponse().getContentAsString();
        }

        @Test
        void userAccessUserProfile_Success() {
            mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:" + port + "/api/auth/user/userProfile")
                    .header("Authorization", "Bearer " + userToken)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("Welcome to User Profile"));
        }

        @Test
        void userAccessAdminProfile_Fail() {
            mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:" + port + "/api/auth/admin/adminProfile")
                    .header("Authorization", "Bearer " + userToken)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                    .andExpect(MockMvcResultMatchers.content().string("{\"errors\":[\"Access Denied\"]}"));
        }

        @Test
        void userAccessManagerProfile_Fail() {
            mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:" + port + "/api/auth/manager/managerProfile")
                    .header("Authorization", "Bearer " + userToken)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                    .andExpect(MockMvcResultMatchers.content().string("{\"errors\":[\"Access Denied\"]}"));
        }
    }
}
