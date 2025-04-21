package com.cherry.med.domain.appointment.validations.schedules;

import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceHourBookingValidator implements AppointmentValidator {

    public void validate(Long patientId, Long doctorId, LocalDateTime dateTime) {
        var minutesGap = Duration.between(dateTime, LocalDateTime.now()).toMinutes();

        if (minutesGap < 30) {
            throw new RuntimeException("Cannot book an appointment with less than 30 minutes in advance");
        }
    }
}
