package br.com.maintenancemanagerservice.dto;

import br.com.maintenancemanagerservice.model.entity.User;
import br.com.maintenancemanagerservice.model.enums.UserRole;

import java.util.List;
import java.util.Set;

public record UserResponseRecord(String id, String name, Set<UserRole> roles, boolean enabled) {

    public static List<UserResponseRecord> userResponseList(List<User> users) {
        return users
                .stream()
                .map(user -> new UserResponseRecord(user.getId(), user.getName(), user.getUserRoles(), user.isEnabled()))
                .toList();
    }
}
