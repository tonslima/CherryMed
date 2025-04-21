package com.cherry.med.domain.appointment.validations.schedules;

import java.time.LocalDateTime;

public interface AppointmentValidator {

    void validate(Long patientId, Long doctorId, LocalDateTime dateTime);
}
