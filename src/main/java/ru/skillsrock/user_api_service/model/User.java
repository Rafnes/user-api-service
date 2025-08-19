package ru.skillsrock.user_api_service.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    private UUID uuid;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "avatar")
    private String avatar;

    @OneToOne
    @JoinColumn(name = "role")
    private Role role;

    public User() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return uuid.equals(user.uuid) && fio.equals(user.fio) && phoneNumber.equals(user.phoneNumber) && avatar.equals(user.avatar) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fio, phoneNumber, avatar, role);
    }

    @Override
    public String toString() {
        return "User {" +
                "uuid = " + uuid +
                ", fio ='" + fio + '\'' +
                ", phoneNumber ='" + phoneNumber + '\'' +
                ", avatar ='" + avatar + '\'' +
                ", role =" + role +
                '}';
    }

}
