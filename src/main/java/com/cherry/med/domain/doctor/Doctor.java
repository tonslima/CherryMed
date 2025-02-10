package com.cherry.med.domain.doctor;

import com.cherry.med.domain.address.Address;
import com.cherry.med.domain.address.AddressDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "doctor")
@Entity(name = "Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Embedded
    private Address address;
    private Boolean active;

    // create constructor
    public Doctor(String name, String email, String phone, String crm, Specialty specialty, Address address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.crm = crm;
        this.specialty = specialty;
        this.address = address;
        this.active = true;
    }

    // update constructor
    public Doctor(String name, String phone, Address address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Doctor update(Doctor update) {
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

    public void delete() {
        this.active = false;
    }
}
