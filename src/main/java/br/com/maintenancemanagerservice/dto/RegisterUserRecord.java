package br.com.maintenancemanagerservice.dto;

import br.com.maintenancemanagerservice.model.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;
import java.util.Set;

public record RegisterUserRecord(@NotBlank String userName, @NotBlank String password, @NotEmpty Set<UserRole> roles) {

    public RegisterUserRecord {
        if(Objects.isNull(userName) && Objects.isNull(password)) {
            throw new IllegalArgumentException("User name and password are required!");
        }

        if (Objects.nonNull(userName) && userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name can't be empty");
        }
        if (Objects.nonNull(roles) && roles.isEmpty()) {
            throw new IllegalArgumentException("At least one role is expected");
        }
    }
}
