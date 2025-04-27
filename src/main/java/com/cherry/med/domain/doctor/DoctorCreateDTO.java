package com.cherry.med.domain.doctor;

import com.cherry.med.domain.address.AddressDTO;
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
        String phone,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        @NotNull
        Specialty specialty,

        @NotBlank
        AddressDTO addressDTO

) {
        public static Doctor toEntity(DoctorCreateDTO dto) {
                return new Doctor(dto.name, dto.email, dto.phone, dto.crm,
                        dto.specialty, AddressDTO.toEntity(dto.addressDTO));
        }
}
