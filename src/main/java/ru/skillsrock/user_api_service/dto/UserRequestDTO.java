package ru.skillsrock.user_api_service.dto;

public class UserRequestDTO {
    private String fio;

    private String phoneNumber;
    private String roleName;

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRoleName() {
        return roleName;
    }

    public UserRequestDTO() {
    }
}
