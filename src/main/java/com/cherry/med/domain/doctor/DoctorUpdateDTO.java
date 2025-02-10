package com.cherry.med.domain.doctor;

import com.cherry.med.domain.address.AddressDTO;

public record DoctorUpdateDTO(

        Long id,
        String name,
        String phone,
        AddressDTO address

) {

    public static Doctor toEntity(DoctorUpdateDTO dto) {
        return new Doctor(dto.name, dto.phone, AddressDTO.toEntity(dto.address));
    }

}
