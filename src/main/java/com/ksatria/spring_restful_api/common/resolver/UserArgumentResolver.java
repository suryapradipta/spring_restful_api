package com.ksatria.spring_restful_api.common.resolver;

import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserRepository userRepository;


    // kalo ada yang minta class User, execute ini first
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // kalo nilainya (true) langsung, semua param di evaluate
        return User.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        String token = httpServletRequest.getHeader("X-API-TOKEN");

        // check apakah ada potensi bug jika menggunakan empty string
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        // unauthorized, bisa token null, atau user ga ketemu
        User user = userRepository.findFirstByToken(token)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        return user;
    }
}


// cara keluar lapisan, pakai throw expcetion, mau menghentikan process pakai ini
/*
* menghindari pakai optional kemudianbunugkus pakai entity Optional<T>, variable yang di return bisa jadi null
*
* optional ada null handling mechanism, gaperlu check if null, sudah lansgung
*
* ini karena banyak customiasasi di dafatarkan ke configurasi,
*
* extend class apa/ implement, kemudian di daftarkan ke condiguration
*
* */
