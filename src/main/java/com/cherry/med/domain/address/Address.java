package com.cherry.med.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String uf;
    private String postalCode;
    private String country;

    public Address(AddressDTO dto) {
        this.street = dto.street();
        this.number = dto.number();
        this.complement = dto.complement();
        this.neighborhood = dto.neighborhood();
        this.city = dto.city();
        this.uf = dto.uf();
        this.postalCode = dto.postalCode();
        this.country = dto.country();
    }

    public void update(Address update) {
        if (update.street != null) {
            this.street = update.street;
        }
        if (update.number != null) {
            this.number = update.number;
        }
        if (update.complement != null) {
            this.complement = update.complement;
        }
        if (update.neighborhood != null) {
            this.neighborhood = update.neighborhood;
        }
        if (update.city != null) {
            this.city = update.city;
        }
        if (update.uf != null) {
            this.uf = update.uf;
        }
        if (update.postalCode != null) {
            this.postalCode = update.postalCode;
        }
        if (update.country != null) {
            this.country = update.country;
        }
    }
}
