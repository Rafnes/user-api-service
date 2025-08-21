package ru.skillsrock.user_api_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillsrock.user_api_service.dto.UserRequestDTO;
import ru.skillsrock.user_api_service.dto.UserResponseDTO;
import ru.skillsrock.user_api_service.exception.PhoneNumberAlreadyTakenException;
import ru.skillsrock.user_api_service.exception.UserAlreadyExistsException;
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
        String phoneNumber = formatPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getPhoneNumber() != null && userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new PhoneNumberAlreadyTakenException("Пользователь с таким номером телефона уже зарегистрирован");
        }
        if (userRepository.existsByFioAndPhoneNumber(userDTO.getFio(), phoneNumber)) {
            throw new UserAlreadyExistsException("Не удалось создать пользователя: пользователь с таким именем и номером телефона уже существует");
        }
        User user = new User();
        user.setFio(userDTO.getFio());

        if (userDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(phoneNumber);
        } else {
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }

        Role role = new Role();
        role.setRoleName(userDTO.getRoleName());
        user.setRole(role);
        userRepository.save(user);

        processAvatar(user, avatar);
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

    public User updateUser(UUID userId, UserRequestDTO userRequestDTO, MultipartFile avatar) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId + " не найден"));
        Validation.validateUserDto(userRequestDTO);
        if (user.getFio().equals(userRequestDTO.getFio()) && user.getPhoneNumber().equals(formatPhoneNumber(userRequestDTO.getPhoneNumber())) && user.getRole().getRoleName().equals(userRequestDTO.getRoleName())) {
            throw new UserAlreadyExistsException("не удалось обновить пользователя: имя, номер телефона и роль совпадают с уже сохраненными");
        }

        //если пользователь пытается обновить номер, который уже закреплен за другим пользователем
        if (userRequestDTO.getPhoneNumber() != null && !userRequestDTO.getPhoneNumber().equals(user.getPhoneNumber()) && userRepository.existsByPhoneNumber(userRequestDTO.getPhoneNumber())) {
            throw new PhoneNumberAlreadyTakenException("Не удалось обновить пользователя: пользователь с таким номером телефона уже зарегистрирован");
        }
        log.info("Обновляем пользователя с id: {}", userId);
        user.setFio(userRequestDTO.getFio());

        if (userRequestDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(formatPhoneNumber(userRequestDTO.getPhoneNumber()));
        } else {
            user.setPhoneNumber(userRequestDTO.getPhoneNumber());
        }

        Role role = roleRepository.findByUuid(user.getRole().getUuid());
        role.setRoleName(userRequestDTO.getRoleName());

        processAvatar(user, avatar);
        userRepository.save(user);
        log.info("Обновлен пользователь: {}", user);
        return user;
    }

    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            log.warn("Не удалось удалить пользователя с id: {} - пользователь не найден", userId);
            throw new UserNotFoundException("Пользователь с id: " + userId + " не найден");
        }
        if (userRepository.findById(userId).get().getAvatar() != null) {
            avatarService.deleteUserAvatar(userId);
        }
        userRepository.deleteById(userId);
        log.info("Удален пользователь с id: {}", userId);
    }

    private void processAvatar(User user, MultipartFile avatar) {
        if (avatar != null) {
            String avatarUrl = null;
            try {
                avatarUrl = avatarService.uploadAvatar(avatar, user.getUuid());
            } catch (IOException ex) {
                log.error("Ошибка загрузки аватара: {}", ex.getMessage());
            }
            user.setAvatar(avatarUrl);
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        return "+" + phoneNumber.replaceAll("\\D", "");
    }
}
