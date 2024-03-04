package com.ksatria.spring_restful_api.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetails {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
