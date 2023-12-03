package br.com.maintenancemanagerservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ResponseLoginRecord(@NotBlank String userToken) {
}
