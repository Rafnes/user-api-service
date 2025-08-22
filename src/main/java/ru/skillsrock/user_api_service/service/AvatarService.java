package ru.skillsrock.user_api_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillsrock.user_api_service.exception.FileDeleteFailureException;
import ru.skillsrock.user_api_service.exception.InvalidFileNameException;
import ru.skillsrock.user_api_service.exception.NullArgumentException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AvatarService {
    private static final Logger log = LoggerFactory.getLogger(AvatarService.class);
    @Value("${avatars.dir}")
    private String avatarDir;

    public String uploadAvatar(MultipartFile avatar, UUID userId) throws IOException {
        if (avatar == null || avatar.isEmpty()) {
            throw new NullArgumentException("Аватар не может быть null или пустым");
        }
        String originalFilename = Objects.requireNonNull(avatar.getOriginalFilename(), "File name cannot be null");
        String extension = getExtension(originalFilename);

        Path filePath = Path.of(avatarDir, "avatar_" + userId + "." + extension);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        Files.createDirectories(filePath.getParent());

        Files.write(filePath, avatar.getBytes());
        log.info("Загружен аватар для пользователя с id :{}", userId);
        return avatarDir + "/" + userId + "." + extension;
    }

    private String getExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new InvalidFileNameException("Имя файла должно содержать расширение");
        }
        return filename.substring(lastDotIndex + 1);
    }

    public void deleteUserAvatar(UUID userId) {
        Path dirPath = Path.of(avatarDir);
        try (var files = Files.list((dirPath))) {
            Optional<Path> fileToDelete = files
                    .filter(path -> path.getFileName().toString().startsWith("avatar_" + userId))
                    .findFirst();
            if (fileToDelete.isPresent()) {
                Files.delete(fileToDelete.get());
                log.info("Удален аватар: {}", fileToDelete.get());
            } else {
                log.warn("не удалось удалить аватар пользователя с id{}: аватар не найден", userId);
            }
        } catch (IOException ex) {
            throw new FileDeleteFailureException("Не удалось удалить аватар пользователя c id: " + userId + ": " + ex.getMessage());
        }
    }
}
