package ru.skillsrock.user_api_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillsrock.user_api_service.exception.InvalidFileNameException;
import ru.skillsrock.user_api_service.exception.NullArgumentException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
public class AvatarService {
    @Value("${avatars.dir}")
    private String avatarDir;

    public String processAvatar(MultipartFile avatar, UUID userId) throws IOException {
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
        return avatarDir + "/" + userId + "." + extension;
    }

    private String getExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new InvalidFileNameException("Имя файла должно содержать расширение");
        }
        return filename.substring(lastDotIndex + 1);
    }
}
