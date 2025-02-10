package com.cherry.med.controller;

import com.cherry.med.domain.doctor.DoctorCreateDTO;
import com.cherry.med.domain.doctor.DoctorDetailedDTO;
import com.cherry.med.domain.patient.Patient;
import com.cherry.med.domain.patient.PatientCreateDTO;
import com.cherry.med.domain.patient.PatientDetailedDTO;
import com.cherry.med.domain.patient.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    @PostMapping
    @Transactional
    public ResponseEntity<PatientDetailedDTO> create(@RequestBody @Valid PatientCreateDTO dto, UriComponentsBuilder uriBuilder) {
        var patient = PatientCreateDTO.toEntity(dto);
        service.create(patient);

        var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new PatientDetailedDTO(patient));
    }



}