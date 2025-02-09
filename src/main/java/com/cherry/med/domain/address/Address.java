package com.cherry.med.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class Address {

    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String uf;
    private String postalCode;
    private String country;


//    public Address(AddressDTO dto) {
//        this.street = dto.street();
//        this.number = dto.number();
//        this.complement = dto.complement();
//        this.neighborhood = dto.neighborhood();
//        this.city = dto.city();
//        this.uf = dto.uf();
//        this.postalCode = dto.postalCode();
//        this.country = dto.country();
//    }
}
