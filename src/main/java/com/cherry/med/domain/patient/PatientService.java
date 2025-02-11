package com.cherry.med.domain.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;

    public Patient create(Patient patient) {
        return repository.save(patient);
    }

    public Patient detail(Long id) {
        return repository.findById(id).get();
    }

    public Page<Patient> list(Pageable pageable) {
        return repository.findAllByActiveTrue(pageable);
    }

    public Patient update(Long id, Patient update) {
        var patient = repository.findById(id).get();

        return patient.update(update);
    }

    public void delete(Long id) {
        repository.findById(id).get().delete();
    }
}
