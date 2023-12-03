package br.com.maintenancemanagerservice.dto;


import jakarta.validation.constraints.NotBlank;

public record RequestLoginRecord(@NotBlank String username, @NotBlank String password) {
}
