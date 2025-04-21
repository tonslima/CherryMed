package com.cherry.med.domain.appointment.validations.schedules;

import com.cherry.med.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ActiveDoctorValidator implements AppointmentValidator {

    private final DoctorRepository doctorRepository;

    @Override
    public void validate(Long patientId, Long doctorId, LocalDateTime dateTime) {
        if (doctorId == null) {
            return;
        }

        if (!doctorRepository.findActiveById(doctorId)) {
            throw new RuntimeException("Doctor is not active");
        }
    }
}
