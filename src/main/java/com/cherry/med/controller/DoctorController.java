package com.cherry.med.controller;

import com.cherry.med.domain.doctor.Doctor;
import com.cherry.med.domain.doctor.DoctorCreateDTO;
import com.cherry.med.domain.doctor.DoctorDetailedDTO;
import com.cherry.med.domain.doctor.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DoctorDetailedDTO> create(@RequestBody @Valid DoctorCreateDTO dto, UriComponentsBuilder uriBuilder) {
        Doctor doctor = DoctorCreateDTO.toEntity(dto);
        service.create(doctor);

        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DoctorDetailedDTO(doctor));
    }

}
