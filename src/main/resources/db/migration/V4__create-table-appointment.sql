CREATE TABLE appointment (
    id                      BIGSERIAL       PRIMARY KEY,
    doctor_id               BIGINT          NOT NULL,
    patient_id              BIGINT          NOT NULL,
    date_time               TIMESTAMP       NOT NULL,

    CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id)
    REFERENCES doctor(id) ON DELETE CASCADE,

    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id)
    REFERENCES patient(id) ON DELETE CASCADE
);