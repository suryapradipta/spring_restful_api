package com.ksatria.spring_restful_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    private String id;

    private String street;

    private String city;

    private String province;

    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @ManyToOne
    // referencedColumnName is optional, can be not specify
//    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    @JoinColumn(name = "contact_id")
    private Contact contact;
}
