package com.cherry.med.repository;

import com.cherry.med.domain.patient.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findAllByActiveTrue(Pageable pageable);


    @Query("""
            select p.active 
            from Patient p
            where
            p.id = :patientId
            """)
    boolean findActiveById(Long patientId);
}
