package com.ksatria.spring_restful_api.service;

import com.ksatria.spring_restful_api.model.RegisterUserRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidationService {

    @Autowired
    private Validator validator;


    public void validate(Object request) {
        //validation service level
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() != 0) {
            // ada error
            throw new ConstraintViolationException(constraintViolations);
        }
    }


}
