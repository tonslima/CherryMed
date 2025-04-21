package com.cherry.med.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record CancelAppointmentDTO(
        @NotNull
        Long id,

        @NotNull
        CancellationReason reason
) {
}
