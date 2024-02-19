package com.ksatria.spring_restful_api.controller;

import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.request.UpdateContactRequest;
import com.ksatria.spring_restful_api.model.response.WebResponse;
import com.ksatria.spring_restful_api.model.request.CreateContactRequest;
import com.ksatria.spring_restful_api.model.response.ContactResponse;
import com.ksatria.spring_restful_api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

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


    // NOTES : SAMPAI LEVEL SERVICE BISA DI RESUSE, BEDA DI CONTROLLERNYA, GRAPHQL PAKAI@CONTROLLER
    /*
    * CONTROLLERNYA SUPAYA RINGAN
    * */

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


}
