package com.ksatria.spring_restful_api.service;

import com.ksatria.spring_restful_api.entity.Address;
import com.ksatria.spring_restful_api.entity.Contact;
import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.request.CreateAddressRequest;
import com.ksatria.spring_restful_api.model.response.AddressResponse;
import com.ksatria.spring_restful_api.repository.AddressRepository;
import com.ksatria.spring_restful_api.repository.ContactRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public AddressResponse create(User user, CreateAddressRequest request) {
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getContactId())
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

        address.setContact(contact);

        addressRepository.save(address);

        return AddressResponse.builder()
            .id(address.getId())
            .street(address.getStreet())
            .city(address.getCity())
            .province(address.getProvince())
            .country(address.getCountry())
            .postalCode(address.getPostalCode())
            .build();
    }

}
