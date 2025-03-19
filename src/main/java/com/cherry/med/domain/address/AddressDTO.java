package com.cherry.med.domain.address;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

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
        @JsonAlias("postal_code")
        String postalCode,

        @NotBlank
        String country

) {
        public static Address toEntity(AddressDTO dto) {
                return new Address(dto.street, dto.number, dto.complement,
                        dto.neighborhood, dto.city, dto.uf, dto.postalCode, dto.country);
        }
        public static Address toEntityUpdate(AddressDTO dto) {
                return new Address(dto);
        }
}
