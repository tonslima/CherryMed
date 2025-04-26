package com.cherry.med.repository;

import com.cherry.med.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Retorna se já existe um agendamento exato
    boolean existsByPatientIdAndDateTime(Long patientId, LocalDateTime dateTime);

    boolean existsByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);
}