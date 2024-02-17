package com.ksatria.spring_restful_api.model.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateContactRequest {

    // supaya tidak dibaca oleh object mapper kalo ada orang yang ngirim id nya
    @JsonIgnore
    @NotBlank
    private String id;

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 100)
    private String phone;

    @Size(max = 100)
    @Email
    private String email;
}
