package br.com.maintenancemanagerservice.dto;

import java.util.List;

public record RegistrationResponseRecord(String id, String userName, List<String> errors) {
    public static RegistrationResponseRecord success(String id, String name) {
        return new RegistrationResponseRecord(id, name, List.of());
    }

    public static RegistrationResponseRecord error(List<String> errors) {
        return new RegistrationResponseRecord(null, null, errors);
    }
}
