package com.ksatria.spring_restful_api.controller;

import com.ksatria.spring_restful_api.entity.Contact;
import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.request.UpdateContactRequest;
import com.ksatria.spring_restful_api.model.response.VoidResponse;
import com.ksatria.spring_restful_api.model.response.WebResponse;
import com.ksatria.spring_restful_api.model.request.CreateContactRequest;
import com.ksatria.spring_restful_api.model.response.ContactResponse;
import com.ksatria.spring_restful_api.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ContactController {

    private final ContactService contactService;

    @PostMapping(
        path = "/api/contacts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> create(User user, @RequestBody CreateContactRequest request) {
        ContactResponse contactResponse = contactService.create(user, request);

        return WebResponse.<ContactResponse>builder()
            .data(contactResponse)
            .build();
    }

    @GetMapping(
        path = "/api/contacts/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> get(User user, @PathVariable String id) {
        ContactResponse contactResponse = contactService.get(user, id);

        return WebResponse.<ContactResponse>builder()
            .data(contactResponse)
            .build();
    }

    @PutMapping(
        path = "/api/contacts/{contactId}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public WebResponse<ContactResponse> update(User user,
                                               @PathVariable("contactId") String contactId,
                                               @RequestBody UpdateContactRequest request) {
        // jangan sampai ada orang yang ngirim request via body
        // jadi kita set id dari id di url
        request.setId(contactId);
        ContactResponse contactResponse = contactService.update(user, request);

        return WebResponse.<ContactResponse>builder()
            .data(contactResponse)
            .build();
    }

    @DeleteMapping(
        path = "/api/contacts/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete (User user, @PathVariable String id) {
        contactService.delete(user, id);

        return WebResponse.<String>builder().data("Ok").build();
    }


    @QueryMapping
    public ContactResponse getContactById(@Argument String id) {
        return contactService.getContactById(id);
    }


    @MutationMapping
    public VoidResponse createContact(@Argument String token,
                                      @Argument CreateContactRequest request) {
        return contactService.createContact(token, request);
    }

//    @QueryMapping
//    public List<Contact> getAllContacts() {
//        return contactService.getAllContacts();
//    }

    @MutationMapping
    public VoidResponse updateContact(@Argument  String token, @Argument UpdateContactRequest request) {
       return contactService.updateContact(token, request);
    }

    @MutationMapping
    public VoidResponse deleteContact(@Argument String token, @Argument String contactId) {
        return contactService.deleteContact(token, contactId);
    }

    @QueryMapping
    public List<Contact> getAllContactsWithUsers() {
        return contactService.getAllContactsWithUsers();
    }

}
