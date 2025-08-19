package ru.skillsrock.user_api_service.service;

import org.springframework.stereotype.Service;
import ru.skillsrock.user_api_service.dto.UserDTO;
import ru.skillsrock.user_api_service.model.User;
import ru.skillsrock.user_api_service.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
