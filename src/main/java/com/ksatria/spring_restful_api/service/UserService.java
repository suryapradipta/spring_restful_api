package com.ksatria.spring_restful_api.service;

import com.ksatria.spring_restful_api.common.security.BCrypt;
import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.LoginUserRequest;
import com.ksatria.spring_restful_api.model.RegisterUserRequest;
import com.ksatria.spring_restful_api.model.UserResponse;
import com.ksatria.spring_restful_api.model.WebResponse;
import com.ksatria.spring_restful_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);

        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);
    }

    public UserResponse get(User user) {
        return UserResponse.builder()
            .username(user.getUsername())
            .name(user.getName())
            .build();
    }

}
