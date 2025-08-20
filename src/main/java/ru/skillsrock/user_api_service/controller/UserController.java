package ru.skillsrock.user_api_service.controller;

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
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/createNewUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> createUser(
            @RequestPart(value = "userDTO") UserRequestDTO userDTO,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        User user = userService.createUser(userDTO, avatar);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<UserResponseDTO> getUser(@RequestParam UUID userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping(value = "/userDetailsUpdate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> updateUser(
            @RequestParam UUID userId,
            @RequestPart(value = "userDTO") UserRequestDTO userDTO,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        User user = userService.updateUser(userId, userDTO, avatar);
        return ResponseEntity.ok(user);
    }
}
