package br.com.maintenancemanagerservice.dto;


import jakarta.validation.constraints.NotBlank;

public record LoginRequestRecordDto(@NotBlank String username, @NotBlank String password) {
}
