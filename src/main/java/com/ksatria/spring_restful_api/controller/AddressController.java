package com.ksatria.spring_restful_api.controller;

import com.ksatria.spring_restful_api.entity.User;
import com.ksatria.spring_restful_api.model.request.CreateAddressRequest;
import com.ksatria.spring_restful_api.model.response.AddressResponse;
import com.ksatria.spring_restful_api.model.response.WebResponse;
import com.ksatria.spring_restful_api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping(
        // reason: if contact is tightly coupled with address
//        path = "/api/contacts/{contactId}/addresses",

        // reason: separate the concern, address can have independent endpoint
        path = "/api/addresses/{contactId}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> create(
        User user,
        @PathVariable("contactId") String contactId,
        @RequestBody CreateAddressRequest request) {

        request.setContactId(contactId);

        AddressResponse addressResponse = addressService.create(user, request);

        return WebResponse.<AddressResponse>builder()
            .data(addressResponse)
            .build();
    }
}
