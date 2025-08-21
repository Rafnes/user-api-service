package ru.skillsrock.user_api_service.dto;

import java.util.UUID;

public class UserResponseDTO {
    private UUID uuid;
    private String fio;
    private String phoneNumber;
    private String roleName;


    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
