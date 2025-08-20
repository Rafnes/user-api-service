package ru.skillsrock.user_api_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillsrock.user_api_service.dto.UserRequestDTO;
import ru.skillsrock.user_api_service.dto.UserResponseDTO;
import ru.skillsrock.user_api_service.exception.UserNotFoundException;
import ru.skillsrock.user_api_service.model.Role;
import ru.skillsrock.user_api_service.model.User;
import ru.skillsrock.user_api_service.repository.RoleRepository;
import ru.skillsrock.user_api_service.repository.UserRepository;
import ru.skillsrock.user_api_service.util.Validation;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AvatarService avatarService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, AvatarService avatarService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.avatarService = avatarService;
    }

    public User createUser(UserRequestDTO userDTO, MultipartFile avatar) {
        Validation.validateUserDto(userDTO);
        User user = new User();
        user.setFio(userDTO.getFio());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        Role role = new Role();
        role.setRoleName(userDTO.getRoleName());
        user.setRole(role);
        userRepository.save(user);

        if (avatar != null) {
            String avatarUrl = null;
            try {
                avatarUrl = avatarService.processAvatar(avatar, user.getUuid());
            } catch (IOException ex) {
                log.error("Ошибка загрузки аватара: {}", ex.getMessage());
            }
            user.setAvatar(avatarUrl);
        }
        log.info("Создан пользователь: {}", user);

        return userRepository.save(user);
    }

    public UserResponseDTO getUser(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + uuid + " не найден"));
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUuid(user.getUuid());
        responseDTO.setFio(user.getFio());
        responseDTO.setPhoneNumber(user.getPhoneNumber());

        String roleName = roleRepository.findByUuid(user.getRole().getUuid()).getRoleName();
        if (roleName.equals("null")) {
            responseDTO.setRoleName(null);
        } else {
            responseDTO.setRoleName(roleName);
        }
        return responseDTO;
    }
}
