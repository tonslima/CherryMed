package com.cherry.med.domain.appointment;

import com.cherry.med.domain.doctor.Specialty;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record AppointmentDTO(

        Long id,

        Long doctorId,

        @NotNull
        Long patientId,

        @NotNull
        @Future
        @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
        LocalDateTime dateTime,

        Specialty specialty
) {
        // Response DTO sending only necessary data
        public static AppointmentDTO fromEntity(Appointment appointment) {
                return new AppointmentDTO(appointment.getId(),
                        appointment.getDoctor().getId(),
                        appointment.getPatient().getId(),
                        appointment.getDateTime(),
                        appointment.getDoctor().getSpecialty());
        }
}
