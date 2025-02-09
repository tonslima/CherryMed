package com.cherry.med.domain.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(

        @NotBlank
        String street,
        String number,
        String complement,

        @NotBlank
        String neighborhood,

        @NotBlank
        String city,

        @NotBlank
        String uf,

        @NotBlank
        String postalCode,

        @NotBlank
        String country

) {
        public static Address toEntity(AddressDTO dto) {
                return new Address(dto.street, dto.number, dto.complement,
                        dto.neighborhood, dto.city, dto.uf, dto.postalCode, dto.country);
        }
}
