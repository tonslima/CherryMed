package com.cherry.med.domain.appointment.validations.schedules;

import com.cherry.med.repository.AppointmentRepository;
import com.cherry.med.repository.PatientRepository;
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

        if (appointmentRepository.findByPatientIdAndDateTimeBetween(patientId, firstHour, lastHour)) {
            throw new RuntimeException("Patient already has an appointment on this date");
        }
    }
}
