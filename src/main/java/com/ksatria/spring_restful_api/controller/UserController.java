package com.ksatria.spring_restful_api.controller;

import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.request.RegisterUserRequest;
import com.ksatria.spring_restful_api.model.request.UpdateUserRequest;
import com.ksatria.spring_restful_api.model.response.UserResponse;
import com.ksatria.spring_restful_api.model.response.VoidResponse;
import com.ksatria.spring_restful_api.model.response.WebResponse;
import com.ksatria.spring_restful_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
        path = "/api/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return WebResponse.<String>builder().data("Ok").build();
    }

    @GetMapping(
        path = "/api/users/current",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> get(User user) {
        UserResponse userResponse = userService.get(user);

        return WebResponse.<UserResponse>builder()
            .data(userResponse).build();
    }

    @PatchMapping(
        path = "/api/users/current",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(User user, @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.update(user, request);
        return WebResponse.<UserResponse>builder()
            .data(userResponse).build();
    }


    @GetMapping(
            path = "/api/users/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }


    @QueryMapping
    public List<UserResponse> allUsers() {
        return userService.getAllUsers();
    }

    @MutationMapping
    public VoidResponse registerUser(@Argument RegisterUserRequest request) {
        return userService.signUp(request);
    }

    @QueryMapping
    public User getCurrentUser(@Argument String token) {
        return userService.currentUser(token);
    }

    @MutationMapping
    public VoidResponse updateUser(@Argument String token, @Argument UpdateUserRequest request) {
        return userService.updateUser(token, request);
    }
}
