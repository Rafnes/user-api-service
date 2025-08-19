package ru.skillsrock.user_api_service.service;

import org.springframework.stereotype.Service;
import ru.skillsrock.user_api_service.dto.UserDTO;
import ru.skillsrock.user_api_service.model.Role;
import ru.skillsrock.user_api_service.model.User;
import ru.skillsrock.user_api_service.repository.RoleRepository;
import ru.skillsrock.user_api_service.repository.UserRepository;
import ru.skillsrock.user_api_service.util.Validation;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User createUser(UserDTO userDTO) {
        Validation.validateUserDto(userDTO);
        User user = new User();
        user.setFio(userDTO.getFio());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAvatar(userDTO.getAvatar());

        Role role = new Role();
        role.setRoleName(userDTO.getRoleName());
        user.setRole(role);

        return userRepository.save(user);
    }
}
