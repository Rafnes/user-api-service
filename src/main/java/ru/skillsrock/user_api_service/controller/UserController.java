package ru.skillsrock.user_api_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillsrock.user_api_service.dto.UserRequestDTO;
import ru.skillsrock.user_api_service.dto.UserResponseDTO;
import ru.skillsrock.user_api_service.model.User;
import ru.skillsrock.user_api_service.service.UserService;

import java.util.UUID;

@RestController
@Tag(name = "Пользователи", description = "Управление учетными записями пользователей")
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Создать пользователя", description = "Создание учетной записи нового пользователя")
    @PostMapping(value = "/createNewUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> createUser(
            @RequestPart(value = "userDTO") UserRequestDTO userDTO,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        User user = userService.createUser(userDTO, avatar);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary = "Получить информацию о пользователе", description = "Получение информации о пользователе по ID")
    @GetMapping("/users")
    public ResponseEntity<UserResponseDTO> getUser(@RequestParam UUID userID) {
        return ResponseEntity.ok(userService.getUser(userID));
    }

    @Operation(summary = "Обновить пользователя", description = "Обновление информации о существующем пользователе")
    @PutMapping(value = "/userDetailsUpdate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> updateUser(
            @RequestParam UUID userId,
            @RequestPart(value = "userDTO") UserRequestDTO userDTO,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        User user = userService.updateUser(userId, userDTO, avatar);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Удалить пользователя", description = "Удаление учетной записи пользователя")
    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(@RequestParam UUID userID) {
        userService.deleteUser(userID);
        return ResponseEntity.noContent().build();
    }
}
