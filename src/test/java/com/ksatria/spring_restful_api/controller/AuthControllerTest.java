package com.ksatria.spring_restful_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksatria.spring_restful_api.model.LoginUserRequest;
import com.ksatria.spring_restful_api.model.TokenResponse;
import com.ksatria.spring_restful_api.model.WebResponse;
import com.ksatria.spring_restful_api.repository.UserRepository;
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
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLoginSuccess() throws Exception {
        LoginUserRequest request = new LoginUserRequest("2", "Surya");
        mockMvc.perform(
            post("/api/users/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<TokenResponse> response =
                objectMapper.readValue(
                    result.getResponse()
                        .getContentAsString(),
                    new TypeReference<>() {
                    });

            assertEquals(TokenResponse.builder()
                .token(response.getData().toString())
                .expiredAt(response.getData().getExpiredAt())
                .build(), response.getData());
        });
    }
}
