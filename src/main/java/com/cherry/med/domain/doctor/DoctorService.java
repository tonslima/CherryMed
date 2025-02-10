package com.cherry.med.domain.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repository;

    public Doctor create(Doctor doctor) {
        return repository.save(doctor);

    }

    public Doctor detail(Long id) {
        return repository.findById(id).get();
    }

    public Page<Doctor> list(Pageable pageable) {
        return repository.findAllByActiveTrue(pageable);
    }

    public Doctor update(Long id, Doctor update) {
        var doctor = repository.findById(id).get();

        return doctor.update(update);
    }

    public void delete(Long id) {
        repository.findById(id).get().delete();
    }
}
