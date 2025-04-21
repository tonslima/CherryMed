package com.cherry.med.repository;

import com.cherry.med.domain.doctor.Doctor;
import com.cherry.med.domain.doctor.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Page<Doctor> findAllByActiveTrue(Pageable pageable);

    @Query("""
            select d from Doctor d
            where
            active = true
            and
            d.specialty = :specialty
            and
            d.id not in(
                select a.doctor.id from Appointment a
                where
                a.dateTime = :dateTime
            )
            order by function('rand')
            limit 1
            """)
    Doctor findRandomAvailableBySpecialty(Specialty specialty, LocalDateTime dateTime);

    @Query("""
            select d.active
            from Doctor d
            where
            d.id = :doctorId
            """)
    boolean findActiveById(Long doctorId);
}
