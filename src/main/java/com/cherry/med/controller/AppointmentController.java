package com.cherry.med.controller;

import com.cherry.med.domain.appointment.AppointmentDTO;
import com.cherry.med.domain.appointment.CancelAppointmentDTO;
import com.cherry.med.service.AppointmentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentDTO> schedule(@RequestBody @Valid AppointmentDTO dto) {
        var appointment = appointmentService.bookAppointment(dto.doctorId(), dto.patientId(), dto.dateTime(), dto.specialty());

        return ResponseEntity.ok(AppointmentDTO.fromEntity(appointment));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> cancel(@RequestBody @Valid CancelAppointmentDTO dto) {
        appointmentService.cancelAppointment(dto.id(), dto.reason());

        return ResponseEntity.noContent().build();
    }
}
