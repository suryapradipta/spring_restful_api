package com.ksatria.spring_restful_api.service;

import com.ksatria.spring_restful_api.common.security.BCrypt;
import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.request.LoginUserRequest;
import com.ksatria.spring_restful_api.model.response.TokenResponse;
import com.ksatria.spring_restful_api.model.response.VoidResponse;
import com.ksatria.spring_restful_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service

public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong"));

        if(BCrypt.checkpw(request.getPassword(), user.getPassword())){
            user.setToken(UUID.randomUUID().toString());
            user.setExpiredTokenAt(next30Days());

            userRepository.save(user);
            return TokenResponse.builder()
                .token(user.getToken())
                .expiredAt(user.getExpiredTokenAt())
                .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong");
        }
    }

    private Long next30Days() {
        return System.currentTimeMillis() + (1000 * 16 * 24 * 30);
    }

    @Transactional
    public void logOut(User user) {
        user.setToken(null);
        user.setExpiredTokenAt(null);

        userRepository.save(user);
    }

    @Transactional
    public User signIn(LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong"));

        if(BCrypt.checkpw(request.getPassword(), user.getPassword())){
            user.setToken(UUID.randomUUID().toString());
            user.setExpiredTokenAt(next30Days());

            userRepository.save(user);
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong");
        }
    }

    @Transactional
    public VoidResponse logOutUser(String token) {
        User user = userRepository.findFirstByToken(token)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        user.setToken(null);
        user.setExpiredTokenAt(null);

        userRepository.save(user);

        return new VoidResponse(true,"User successfully logged out." );

    }
}
