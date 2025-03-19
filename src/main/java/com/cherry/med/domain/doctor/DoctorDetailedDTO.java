package com.cherry.med.domain.doctor;

import com.cherry.med.domain.address.Address;

public record DoctorDetailedDTO(

        Long id,
        String name,
        String email,
        String phone,
        String crm,
        Specialty specialty,
        Address address
) {

    public DoctorDetailedDTO(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getPhone(),
                doctor.getCrm(), doctor.getSpecialty(), doctor.getAddress());
    }

}
