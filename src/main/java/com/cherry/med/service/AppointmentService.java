package com.cherry.med.service;

import com.cherry.med.domain.appointment.Appointment;
import com.cherry.med.domain.appointment.CancellationReason;
import com.cherry.med.domain.appointment.validations.cancellation.CancellationValidator;
import com.cherry.med.domain.appointment.validations.schedules.AppointmentValidator;
import com.cherry.med.domain.doctor.Doctor;
import com.cherry.med.domain.doctor.Specialty;
import com.cherry.med.repository.DoctorRepository;
import com.cherry.med.repository.PatientRepository;
import com.cherry.med.repository.AppointmentRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final List<AppointmentValidator> appointmentValidators;
    private final List<CancellationValidator> cancellationValidators;

    public Appointment bookAppointment(Long idDoctor, Long idPatient, LocalDateTime dateTime, Specialty specialty) {
        // validates provided patient id
        if (!patientRepository.existsById(idPatient)) {
            throw new ValidationException("Patient not found");
        }

        // validates provided doctor id
        if (idDoctor != null && !doctorRepository.existsById(idDoctor)) {
            throw new ValidationException("Doctor not found");
        }

        // runs appointment validations
        appointmentValidators.forEach(validator -> validator.validate(idPatient, idDoctor, dateTime));

        // creates appointment
        var patient = patientRepository.getReferenceById(idPatient);
        var doctor = findDoctor(idDoctor, specialty, dateTime);
        var appointment = Appointment.create(doctor, patient, dateTime);
        return appointmentRepository.save(appointment);
    }

    public Doctor findDoctor(Long idDoctor, Specialty specialty, LocalDateTime dateTime) {
        if (idDoctor != null) {
            return doctorRepository.getReferenceById(idDoctor);
        }

        if (specialty == null) {
            throw new ValidationException("Specialty is required when doctor id is not sent");
        }

        return doctorRepository.findRandomAvailableBySpecialty(specialty, dateTime);
    }

    public void cancelAppointment(Long appointmentId, CancellationReason reason) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new ValidationException("Appointment not found");
        }

        // runs cancellation validations
        cancellationValidators.forEach(validator -> validator.validate(appointmentId, LocalDateTime.now()));

        appointmentRepository.getReferenceById(appointmentId)
                .cancel(reason);
    }
}
