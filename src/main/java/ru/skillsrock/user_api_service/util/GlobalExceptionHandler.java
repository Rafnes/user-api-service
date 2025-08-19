package ru.skillsrock.user_api_service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.skillsrock.user_api_service.exception.IllegalPhoneNumberException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalPhoneNumberException.class)
    public ResponseEntity<String> handleIllegalPhoneNumberException(IllegalPhoneNumberException ex) {
        log.warn("Ошибка валидации номера телефона: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
