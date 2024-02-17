package com.ksatria.spring_restful_api.service;

import com.ksatria.spring_restful_api.entity.Contact;
import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.request.CreateContactRequest;
import com.ksatria.spring_restful_api.model.request.UpdateContactRequest;
import com.ksatria.spring_restful_api.model.response.ContactResponse;
import com.ksatria.spring_restful_api.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

        return toContactResponse(contact);
    }

    public ContactResponse toContactResponse(Contact contact) {
        return ContactResponse.builder()
            .id(contact.getId())
            .firstName(contact.getFirstName())
            .lastName(contact.getLastName())
            .phone(contact.getPhone())
            .email(contact.getEmail())
            .build();
    }

    @Transactional(readOnly = true)
    public ContactResponse get(User user, String id) {
        Contact contact = contactRepository.findFirstByUserAndId(user, id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        return toContactResponse(contact);
    }

    @Transactional
    public ContactResponse update(User user, UpdateContactRequest request) {
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getId())
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setPhone(request.getPhone());
        contact.setEmail(request.getEmail());

        contactRepository.save(contact);

        return toContactResponse(contact);

    }
}
