package com.cherry.med.domain.patient;

import com.cherry.med.domain.address.Address;

public record PatientDetailedDTO(

        Long id,
        String name,
        String email,
        String phone,
        String cpf,
        Address address

) {
    public PatientDetailedDTO(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getPhone(),
                patient.getCpf(), patient.getAddress());
    }
}
