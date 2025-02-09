package com.cherry.med.domain.doctor;

import com.cherry.med.domain.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DoctorCreateDTO(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{20}")
        String phone,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        @NotNull
        Specialty specialty,

        @NotNull
        @Valid
        AddressDTO address

) {
        public static Doctor toEntity(DoctorCreateDTO dto) {
                return new Doctor(dto.name, dto.email, dto.phone, dto.crm,
                        dto.specialty, AddressDTO.toEntity(dto.address));
        }
}
