package com.cherry.med.domain.appointment.validations.cancellation;

import com.cherry.med.domain.appointment.AppointmentStatus;
import com.cherry.med.repository.AppointmentRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdvanceHourCancellationValidator implements CancellationValidator {

    private final AppointmentRepository appointmentRepository;

    @Override
    public void validate(Long appointmentId, LocalDateTime dateTime) {
        var appointment = appointmentRepository.getReferenceById(appointmentId);
        var now = LocalDateTime.now();

        var hourGap = Duration.between(now, appointment.getDateTime()).toHours();

        if (appointment.getStatus().equals(AppointmentStatus.CANCELLED)) {
            throw new ValidationException("Appointment already cancelled");
        }

        if (hourGap < 24) {
            throw new ValidationException("Cannot cancel appointment with less than 24 hours in advance");
        }
    }
}
