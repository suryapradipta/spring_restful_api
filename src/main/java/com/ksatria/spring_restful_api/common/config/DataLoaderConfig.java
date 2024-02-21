package com.ksatria.spring_restful_api.common.config;

import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.service.UserService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class DataLoaderConfig {

    @Bean
    public DataLoader<String, User> userLoader(UserService userService) {
        return DataLoader.newDataLoader(keys -> CompletableFuture.supplyAsync(() ->
                userService.getUsersByIds(keys)
        ));
    }
}