package br.com.maintenancemanagerservice.dto;

import br.com.maintenancemanagerservice.model.enums.UserRole;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

public record UpdateUserRecord(@NotBlank String id, String name, List<UserRole> roles) {
    public UpdateUserRecord {
        if(Objects.isNull(name) && Objects.isNull(roles)) {
            throw new IllegalArgumentException("Invalid request");
        }

        if (Objects.nonNull(name) && name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name can't be empty");
        }
        if (Objects.nonNull(roles) && roles.isEmpty()) {
            throw new IllegalArgumentException("At least one role is expected");
        }
    }
}
