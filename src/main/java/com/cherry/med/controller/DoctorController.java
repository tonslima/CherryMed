package com.cherry.med.controller;

import com.cherry.med.domain.doctor.*;
import com.cherry.med.service.DoctorService;
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
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DoctorDetailedDTO> create(@RequestBody @Valid DoctorCreateDTO dto, UriComponentsBuilder uriBuilder) {
        var doctor = DoctorCreateDTO.toEntity(dto);
        service.create(doctor);

        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DoctorDetailedDTO(doctor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailedDTO> detail(@PathVariable Long id) {
        var doctor = service.detail(id);

        return ResponseEntity.ok(new DoctorDetailedDTO(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<DoctorListDTO>> list(@PageableDefault(page = 0, size = 10, sort = {"name"}) Pageable pageable) {
        var page = service.list(pageable).map(DoctorListDTO::new);

        return ResponseEntity.ok(page);
    }

    @PatchMapping("/{id}/update")
    @Transactional
    public ResponseEntity<DoctorDetailedDTO> update(@PathVariable Long id, @RequestBody @Valid DoctorUpdateDTO dto) {
        var update = DoctorUpdateDTO.toEntity(dto);
        var doctor = service.update(id, update);

        return ResponseEntity.ok(new DoctorDetailedDTO(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
