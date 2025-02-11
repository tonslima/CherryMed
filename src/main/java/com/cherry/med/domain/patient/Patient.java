package com.cherry.med.domain.patient;

import com.cherry.med.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "patient")
@Entity(name = "Patient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cpf;

    @Embedded
    private Address address;
    private Boolean active;

    // create constructor
    public Patient(String name, String email, String phone, String cpf, Address address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.address = address;
        this.active = true;
    }

    // update constructor
    public Patient(String name, String phone, Address address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }


    public Patient update(Patient update) {
        if (update.name != null) {
            this.name = update.name;
        }
        if (update.phone != null) {
            this.phone = update.phone;
        }
        if (update.address != null) {
            this.address.update(update.address);
        }
        return this;
    }
}