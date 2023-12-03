package br.com.maintenancemanagerservice.dto;

import br.com.maintenancemanagerservice.model.enums.UserRole;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserRecord(@NotBlank String userName, @NotBlank String password, UserRole role) {
}
