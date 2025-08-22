package ru.skillsrock.user_api_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFileNameException extends RuntimeException {
    public InvalidFileNameException(String message) {
        super(message);
    }
}
