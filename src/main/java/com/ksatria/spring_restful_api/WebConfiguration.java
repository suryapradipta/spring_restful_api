package com.ksatria.spring_restful_api;

import com.ksatria.spring_restful_api.common.resolver.UserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

// pertama kali di inject, container
@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    // tidak disarankan auto wired
    private final UserArgumentResolver userArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);

        resolvers.add(userArgumentResolver);
    }
}
