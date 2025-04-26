package com.cherry.med.domain.appointment.validations.schedules;

import com.cherry.med.repository.AppointmentRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AppointmentConflictByPatientValidator implements AppointmentValidator {

    private final AppointmentRepository appointmentRepository;

    @Override
    public void validate(Long patientId, Long doctorId, LocalDateTime dateTime) {
        var firstHour = dateTime.withHour(7);
        var lastHour = dateTime.withHour(18);

        if (appointmentRepository.existsByPatientIdAndDateTime(patientId, dateTime)) {
            throw new ValidationException("Patient already has an appointment on this date");
        }
    }
}
