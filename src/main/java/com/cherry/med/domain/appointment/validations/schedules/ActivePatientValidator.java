package com.cherry.med.domain.appointment.validations.schedules;

import com.cherry.med.repository.PatientRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ActivePatientValidator implements AppointmentValidator {

    private final PatientRepository patientRepository;

    @Override
    public void validate(Long patientId, Long doctorId, LocalDateTime dateTime) {

        if (!patientRepository.findActiveById(patientId)) {
            throw new ValidationException("Patient is not active");
        }
    }
}
