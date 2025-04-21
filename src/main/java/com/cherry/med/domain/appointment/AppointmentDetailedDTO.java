package com.cherry.med.domain.appointment;

import com.cherry.med.domain.doctor.Doctor;
import com.cherry.med.domain.patient.Patient;

import java.time.LocalDateTime;

public record AppointmentDetailedDTO(

        Long id,
        Doctor doctor,
        Patient patient,
        LocalDateTime dateTime
) {
    public AppointmentDetailedDTO(Appointment appointment) {
        this(appointment.getId(), appointment.getDoctor(), appointment.getPatient(), appointment.getDateTime());
    }
}
