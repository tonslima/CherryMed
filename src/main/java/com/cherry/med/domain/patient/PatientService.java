package com.cherry.med.domain.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;

    public Patient create(Patient patient) {
        return repository.save(patient);
    }
}
