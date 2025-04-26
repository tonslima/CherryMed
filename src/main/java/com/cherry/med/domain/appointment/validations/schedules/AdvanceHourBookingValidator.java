package com.cherry.med.domain.appointment.validations.schedules;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceHourBookingValidator implements AppointmentValidator {

    @Override
    public void validate(Long patientId, Long doctorId, LocalDateTime dateTime) {
        var minutesGap = Duration.between(LocalDateTime.now(), dateTime).toMinutes();
        System.out.println(minutesGap);

        if (minutesGap < 30) {
            throw new ValidationException("Cannot book an appointment with less than 30 minutes in advance");
        }
    }
}
