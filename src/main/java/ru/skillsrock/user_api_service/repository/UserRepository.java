package ru.skillsrock.user_api_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillsrock.user_api_service.model.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public boolean existsByPhoneNumber(String phoneNumber);
    public boolean existsByFioAndPhoneNumber(String fio, String phoneNumber);
}
