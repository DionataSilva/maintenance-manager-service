package br.com.maintenancemanagerservice.dto;

import br.com.maintenancemanagerservice.model.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RegisterRecordDTO(@NotBlank String userName, @NotBlank String password, UserRole role) {
}
