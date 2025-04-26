package com.cherry.med.controller;

import com.cherry.med.domain.patient.PatientCreateDTO;
import com.cherry.med.domain.patient.PatientDetailedDTO;
import com.cherry.med.domain.patient.PatientListDTO;
import com.cherry.med.domain.patient.PatientUpdateDTO;
import com.cherry.med.service.PatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
@SecurityRequirement(name = "bearer-key")
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

    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailedDTO> detail(@PathVariable Long id) {
        var patient = service.detail(id);

        return ResponseEntity.ok().body(new PatientDetailedDTO(patient));
    }

    @GetMapping
    public ResponseEntity<Page<PatientListDTO>> list(@PageableDefault(page = 0, size = 10, sort = {"name"}) Pageable pageable) {
        var page = service.list(pageable).map(PatientListDTO::new);

        return ResponseEntity.ok(page);
    }

    @PatchMapping("/{id}/update")
    @Transactional
    public ResponseEntity<PatientDetailedDTO> update(@PathVariable Long id, @RequestBody PatientUpdateDTO dto) {
        var update = PatientUpdateDTO.toEntity(dto);
        var patient = service.update(id, update);

        return ResponseEntity.ok(new PatientDetailedDTO(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}