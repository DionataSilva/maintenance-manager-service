package br.com.maintenancemanagerservice.model.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String role;

    UserRole(String role){
        this.role = role;
    }
}