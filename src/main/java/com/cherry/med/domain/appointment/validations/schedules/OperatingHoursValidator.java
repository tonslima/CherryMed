package com.cherry.med.domain.appointment.validations.schedules;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class OperatingHoursValidator implements AppointmentValidator {

    @Override
    public void validate(Long patientId, Long doctorId, LocalDateTime dateTime) {
        boolean sunday = dateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean beforeOpening = dateTime.getHour() < 7;
        boolean afterClosing = dateTime.getHour() > 19;

        if(sunday || beforeOpening || afterClosing) {
            throw new ValidationException("Out of clinic operating hours");
        }
    }
}
