package com.ksatria.spring_restful_api.service;

import com.ksatria.spring_restful_api.entity.Contact;
import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.request.CreateContactRequest;
import com.ksatria.spring_restful_api.model.response.ContactResponse;
import com.ksatria.spring_restful_api.repository.ContactRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public ContactResponse create(User user, CreateContactRequest request) {
        validationService.validate(request);

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setPhone(request.getPhone());
        contact.setEmail(request.getEmail());
        contact.setUser(user);

        contactRepository.save(contact);

        return ContactResponse.builder()
            .id(contact.getId())
            .firstName(contact.getFirstName())
            .lastName(contact.getLastName())
            .phone(contact.getPhone())
            .email(contact.getEmail())
            .build();
    }

}
