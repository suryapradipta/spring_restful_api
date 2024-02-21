package com.ksatria.spring_restful_api.common.component;

import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.repository.UserRepository;
import org.dataloader.BatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class UserDataLoader {
//    @Autowired
//    private UserRepository userRepository;
//
//    public BatchLoader<String, User> userBatchLoader() {
//        return keys -> CompletableFuture.supplyAsync(() ->
//                userRepository.findAllById(keys)
//        );
//    }
}
