package com.ksatria.spring_restful_api.service;

import com.ksatria.spring_restful_api.common.security.BCrypt;
import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.request.RegisterUserRequest;
import com.ksatria.spring_restful_api.model.request.UpdateUserRequest;
import com.ksatria.spring_restful_api.model.response.UserResponse;
import com.ksatria.spring_restful_api.model.response.VoidResponse;
import com.ksatria.spring_restful_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ValidationService validationService;

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

    @Transactional
    public UserResponse get(User user) {
        return UserResponse.builder()
            .username(user.getUsername())
            .name(user.getName())
            .build();
    }

    @Transactional
    public UserResponse update(User user, UpdateUserRequest request) {
        validationService.validate(request);

        if(Objects.nonNull(request.getName())) {
            user.setName(request.getName());
        }

        if(Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(user);

        return UserResponse.builder()
            .name(user.getName())
            .username(user.getUsername())
            .build();
    }

    @Transactional
    public VoidResponse updateUser(String token, UpdateUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findFirstByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        if(Objects.nonNull(request.getName())) {
            user.setName(request.getName());
        }

        if(Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(user);

        return new VoidResponse(
                true,
                "User successfully updated"
        );
    }



    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    public VoidResponse signUp(RegisterUserRequest request) {
        validationService.validate(request);

        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);

        return new VoidResponse(
                true,
                "User successfully registered"
        );
    }


    @Transactional
    public User currentUser(String token) {

        return userRepository.findFirstByToken(token)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    }

    @Transactional
    public List<User> getUsersByIds(List<String> userIds) {
        // Fetch users by IDs from the repository
        return userRepository.findAllById(userIds);
    }
}
