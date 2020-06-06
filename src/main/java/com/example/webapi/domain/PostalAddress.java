package com.example.webapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostalAddress {

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String locality;

    @Column(length = 100)
    private String region;

    @Column(length = 100)
    private String postOfficeBoxNumber;

    @Column(length = 32)
    private String postalCode;

    @Column(length = 100)
    private String street;
}
