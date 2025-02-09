package com.cherry.med.domain.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repository;

    public Doctor create(Doctor doctor) {
        return repository.save(doctor);

    }
}
