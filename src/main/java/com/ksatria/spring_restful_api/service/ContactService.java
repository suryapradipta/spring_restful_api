package com.ksatria.spring_restful_api.service;

import com.ksatria.spring_restful_api.entity.Contact;
import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.request.CreateContactRequest;
import com.ksatria.spring_restful_api.model.request.UpdateContactRequest;
import com.ksatria.spring_restful_api.model.response.ContactResponse;
import com.ksatria.spring_restful_api.model.response.VoidResponse;
import com.ksatria.spring_restful_api.repository.ContactRepository;
import com.ksatria.spring_restful_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    private final UserRepository userRepository;

    private final ValidationService validationService;

    @Autowired
    private DataLoader<String, User> userLoader;

    public ContactResponse toContactResponse(Contact contact) {
        return ContactResponse.builder()
            .id(contact.getId())
            .firstName(contact.getFirstName())
            .lastName(contact.getLastName())
            .phone(contact.getPhone())
            .email(contact.getEmail())
            .build();
    }

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

    @Transactional
    public void delete(User user, String id) {
        Contact contact = contactRepository.findFirstByUserAndId(user, id)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contactRepository.deleteById(contact.getId());
    }

    @Transactional
    public ContactResponse getContactById(String contactId) {
        Contact contact = contactRepository.findById(contactId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Contact Not Found"
            ));

        return toContactResponse(contact);
    }

    @Transactional
    public VoidResponse createContact(String token, CreateContactRequest request) {
        validationService.validate(request);

        User user = userRepository.findFirstByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setPhone(request.getPhone());
        contact.setEmail(request.getEmail());
        contact.setUser(user);

        contactRepository.save(contact);

        return new VoidResponse(
                true,
                "Contact successfully registered"
        );
    }

    @Transactional
    public List<Contact> getAllContacts() {
        return contactRepository.getAllContacts();
    }

    @Transactional
    public VoidResponse updateContact(String token, UpdateContactRequest request) {
        validationService.validate(request);

        User user = userRepository.findFirstByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setPhone(request.getPhone());
        contact.setEmail(request.getEmail());

        contactRepository.save(contact);

        return new VoidResponse(
                true,
                "Contact successfully updated"
        );
    }

    @Transactional
    public VoidResponse deleteContact(String token, String contactId) {
        User user = userRepository.findFirstByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contactRepository.deleteById(contact.getId());

        return new VoidResponse(
                true,
                "Contact successfully deleted"
        );
    }

    public List<Contact> getAllContactsWithUsers() {
        List<Contact> contacts = contactRepository.getAllContacts();

        // Load users for contacts using the data loader
        contacts.forEach(contact -> userLoader.load(contact.getUser().getName()));

        return contacts;
    }

}
