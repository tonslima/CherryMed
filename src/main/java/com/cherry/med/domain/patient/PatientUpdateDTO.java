package com.cherry.med.domain.patient;

import com.cherry.med.domain.address.AddressDTO;

public record PatientUpdateDTO (

        Long id,
        String name,
        String phone,
        AddressDTO address

){

    public static Patient toEntity(PatientUpdateDTO dto) {
        return new Patient(dto.name, dto.phone, AddressDTO.toEntity(dto.address));
    }

}
