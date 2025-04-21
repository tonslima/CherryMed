package com.cherry.med.domain.appointment.validations.cancellation;

import java.time.LocalDateTime;

public interface CancellationValidator {

    void validate (Long appointmentId, LocalDateTime dateTime);
}
