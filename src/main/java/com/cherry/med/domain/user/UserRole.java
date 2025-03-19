package com.cherry.med.domain.user;

public enum UserRole {

    ADMIN("admin"),
    DOCTOR("doctor"),
    PATIENT("patient");

    String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
