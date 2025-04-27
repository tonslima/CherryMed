package com.cherry.med.controller;

import com.cherry.med.domain.address.Address;
import com.cherry.med.domain.appointment.Appointment;
import com.cherry.med.domain.appointment.AppointmentDTO;
import com.cherry.med.domain.appointment.AppointmentStatus;
import com.cherry.med.domain.doctor.Doctor;
import com.cherry.med.domain.doctor.Specialty;
import com.cherry.med.domain.patient.Patient;
import com.cherry.med.service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    JacksonTester<AppointmentDTO> appointmentDTOJacksonTester;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, objectMapper);
    }

    @MockitoBean
    private AppointmentService appointmentService;

    @Test
    @DisplayName("Must return http 400 when data is invalid")
    @WithMockUser(username = "testuser", roles = {"ADMIN"}) // Mock an ADMIN authenticated user
    void scheduleCase1() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/appointments")
                        .header("Authorization", "Bearer fake-token"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Must return http 200 when data is valid")
    @WithMockUser(username = "testuser", roles = {"ADMIN"}) // Mock an ADMIN authenticated user
    void scheduleCase2() throws Exception {
        var date = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.CARDIOLOGY;
        var address = new Address("Rua gamer", "10", "casa 1", "Vila marica", "São Paulo", "SP", "1029129", "Brasil");
        var doctor = new Doctor(1l, "Bile", "bile@gmail.com", "119283909040", "2342", specialty, address, true);
        var patient = new Patient(1l, "Felipe", "felipe@gmail.com", "11923903928", "10293847372", address, true);
        var appointment = new Appointment(1l, doctor, patient, date, AppointmentStatus.SCHEDULED, null);

        when(appointmentService.bookAppointment(any(), any(), any(), any()))
                .thenReturn(appointment);

        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/appointments")
                        .header("Authorization", "Bearer fake-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appointmentDTOJacksonTester.write(
                                new AppointmentDTO(1l, 1l, 1l, date, specialty)
                        ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = appointmentDTOJacksonTester.write(
                new AppointmentDTO(1l, 1l, 1l, date, specialty)
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}