package ru.skillsrock.user_api_service.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "role_n ame", nullable = false)
    private String roleName;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Role role = (Role) obj;
        return uuid.equals(role.uuid) && roleName.equals(role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, roleName);
    }

    @Override
    public String toString() {
        return "Role {" +
                "uuid = " + uuid +
                ", roleName ='" + roleName + '\'' +
                '}';
    }
}
