package com.ksatria.spring_restful_api.controller;

import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.request.LoginUserRequest;
import com.ksatria.spring_restful_api.model.response.TokenResponse;
import com.ksatria.spring_restful_api.model.response.VoidResponse;
import com.ksatria.spring_restful_api.model.response.WebResponse;
import com.ksatria.spring_restful_api.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(
        path = "/api/auth/login",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);

        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }

    @DeleteMapping(
        path = "/api/auth/logout",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logOut(User user) {
        authService.logOut(user);

        return WebResponse.<String>builder().data("Ok").build();
    }

    @QueryMapping
    public User loginUser(@Argument LoginUserRequest request) {
        return authService.signIn(request);
    }

    @MutationMapping
    public VoidResponse logOutUser(@Argument  String token) {
        VoidResponse voidResponse = authService.logOutUser(token);

        return voidResponse;
    }
}
