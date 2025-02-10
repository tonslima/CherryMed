package com.cherry.med.domain.patient;

import com.cherry.med.domain.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PatientCreateDTO(

        @NotBlank
        String name,

        @Email
        String email,

        @NotBlank
        String phone,

        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String cpf,

        @NotNull
        @Valid
        AddressDTO address
) {

    public static Patient toEntity(PatientCreateDTO dto) {
        return new Patient(dto.name, dto.email, dto.phone,
                dto.cpf, AddressDTO.toEntity(dto.address));
    }
}
