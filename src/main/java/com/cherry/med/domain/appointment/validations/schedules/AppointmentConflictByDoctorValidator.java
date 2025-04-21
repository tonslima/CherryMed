package com.cherry.med.domain.appointment.validations.schedules;

import com.cherry.med.repository.AppointmentRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AppointmentConflictByDoctorValidator implements AppointmentValidator {

    private final AppointmentRepository appointmentRepository;

    @Override
    public void validate(Long patientId, Long doctorId, LocalDateTime dateTime) {

        if (appointmentRepository.findByDoctorIdAndDateTime(doctorId, dateTime)) {
            throw new ValidationException("Doctor already has an appointment on this date");
        }
    }
}
