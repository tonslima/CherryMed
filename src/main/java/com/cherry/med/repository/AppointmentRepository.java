package com.cherry.med.repository;

import com.cherry.med.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean findByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);

    boolean findByPatientIdAndDateTimeBetween(Long patientId, LocalDateTime firstHour, LocalDateTime lastHour);
}
