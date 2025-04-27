package com.cherry.med.repository;

import com.cherry.med.domain.address.AddressDTO;
import com.cherry.med.domain.appointment.Appointment;
import com.cherry.med.domain.doctor.Doctor;
import com.cherry.med.domain.doctor.DoctorCreateDTO;
import com.cherry.med.domain.doctor.Specialty;
import com.cherry.med.domain.patient.Patient;
import com.cherry.med.domain.patient.PatientCreateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("must return null when there is no available specialist doctor at the given date")
    void findRandomAvailableBySpecialtyCase1() {
        // given or arrange
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(LocalTime.of(10, 0));
        var address = addressDTO();
        var doctor = createDoctor("Mauro test",
                "test.mauro@gmail.com",
                "11982828283",
                "2332",
                Specialty.CARDIOLOGY,
                address
        );
        var patient = createPatient("Patricia test",
                "test.patricia@gmail.com",
                "1192839485738",
                "29304930299",
                address
        );
        bookAppointment(doctor, patient, nextMondayAt10);

        // when or act
        var availableDoctor = doctorRepository.findRandomAvailableBySpecialty(Specialty.CARDIOLOGY, nextMondayAt10);

        // then or assert
        assertThat(availableDoctor).isNull();
    }

    @Test
    @DisplayName("must return doctor when there is a specialist doctor available at the given date")
    void findRandomAvailableBySpecialtyCase2() {
        // given or arrange
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(LocalTime.of(10, 0));

        var address = addressDTO();
        var doctor = createDoctor("Mauro test",
                "test.mauro@gmail.com",
                "11982828283",
                "2332",
                Specialty.CARDIOLOGY,
                address
        );

        // when or act
        var availableDoctor = doctorRepository.findRandomAvailableBySpecialty(Specialty.CARDIOLOGY, nextMondayAt10);

        // then or assert
        assertThat(availableDoctor).isEqualTo(doctor);
    }

    @Test
    @DisplayName("must return a single random doctor when there are multiple doctors available at the given date")
    void findRandomAvailableBySpecialtyCase3() {
        // given or arrange
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(LocalTime.of(10, 0));

        var address = addressDTO();
        var doctor = createDoctor("Mauro test",
                "test.mauro@gmail.com",
                "11980828283",
                "2330",
                Specialty.CARDIOLOGY,
                address
        );
        var doctor1 = createDoctor("Pablo test",
                "test.pablo@gmail.com",
                "11981828283",
                "2331",
                Specialty.CARDIOLOGY,
                address
        );
        var doctor2 = createDoctor("Renato test",
                "test.renato@gmail.com",
                "11982828223",
                "2332",
                Specialty.CARDIOLOGY,
                address
        );

        // when or act
        Set<Doctor> selectedDoctors = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Doctor availableDoctor = doctorRepository.findRandomAvailableBySpecialty(Specialty.CARDIOLOGY, nextMondayAt10);
            selectedDoctors.add(availableDoctor);
        }

        // then or assert
        assertThat(selectedDoctors.size()).isGreaterThan(1);
    }

    private void bookAppointment(Doctor doctor, Patient patient, LocalDateTime dateTime) {
        entityManager.persist(Appointment.create(doctor, patient, dateTime));
    }

    private Doctor createDoctor(String name, String email, String phone, String crm, Specialty specialty, AddressDTO address) {
        var doctor = DoctorCreateDTO.toEntity(doctorDTO(name, email, phone, crm, specialty, address));
        entityManager.persist(doctor);
        return doctor;
    }

    private Patient createPatient(String name, String email, String phone, String cpf, AddressDTO address) {
        var patient = PatientCreateDTO.toEntity(patientDTO(name, email, phone, cpf, address));
        entityManager.persist(patient);
        return patient;
    }

    private DoctorCreateDTO doctorDTO(String name, String email, String phone, String crm, Specialty specialty, AddressDTO address) {
        return new DoctorCreateDTO(
                name,
                email,
                phone,
                crm,
                specialty,
                address
        );
    }

    private PatientCreateDTO patientDTO(String name, String email, String phone, String cpf, AddressDTO address) {
        return new PatientCreateDTO(
                name,
                email,
                phone,
                cpf,
                addressDTO()
        );
    }

    private AddressDTO addressDTO() {
        return new AddressDTO(
                "mauro bocardi",
                "139",
                "casa 2",
                "vila sésamo",
                "São Paulo",
                "SP",
                "02923293",
                "Brasil"
        );
    }
}

