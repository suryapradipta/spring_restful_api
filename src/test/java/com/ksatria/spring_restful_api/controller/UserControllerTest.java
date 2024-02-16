package com.ksatria.spring_restful_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.RegisterUserRequest;
import com.ksatria.spring_restful_api.model.WebResponse;
import com.ksatria.spring_restful_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRegisterSuccess() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("suryapradipta");
        request.setPassword("rahasia");
        request.setUsername("surya");

        mockMvc.perform(
            post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<String> response =
                objectMapper.readValue(
                    result.getResponse()
                        .getContentAsString(),
                    new TypeReference<WebResponse<String>>() {
                    });

            assertEquals("Ok", response.getData());
        });
    }

    @Test
    void testRegisterBadRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("");
        request.setPassword("");
        request.setUsername("");

        mockMvc.perform(
            post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
            status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response =
                objectMapper.readValue(
                    result.getResponse()
                        .getContentAsString(),
                    new TypeReference<WebResponse<String>>() {
                    });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testDuplicateUser() throws Exception {
        User user = new User();
        user.setName("test");
        user.setPassword("test");
        user.setUsername("test");
        userRepository.save(user);

        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("test");
        request.setPassword("test");
        request.setUsername("test");

        mockMvc.perform(
            post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
            status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response =
                objectMapper.readValue(
                    result.getResponse()
                        .getContentAsString(),
                    new TypeReference<WebResponse<String>>() {
                    });

            assertNotNull(response.getErrors());
        });
    }
}
