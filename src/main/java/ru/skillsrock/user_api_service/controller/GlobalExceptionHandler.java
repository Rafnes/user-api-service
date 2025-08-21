package ru.skillsrock.user_api_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.skillsrock.user_api_service.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalPhoneNumberException.class)
    public ResponseEntity<String> handleIllegalPhoneNumberException(IllegalPhoneNumberException ex) {
        log.warn("Ошибка валидации номера телефона: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidStringArgumentException.class)
    public ResponseEntity<String> handleInvalidStringArgumentException(InvalidStringArgumentException ex) {
        log.warn("Ошибка валидации пользователя: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NullArgumentException.class)
    public ResponseEntity<String> handleNullArgumentException(NullArgumentException ex) {
        log.warn("Передано значение null: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidFileNameException.class)
    public ResponseEntity<String> handleInvalidFileNameException(InvalidFileNameException ex) {
        log.warn("Некорректное название файла: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        log.warn("Пользователь не найден: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(FileDeleteFailureException.class)
    public ResponseEntity<String> handleFileDeleteFailureException(FileDeleteFailureException ex) {
        log.warn("Не удалось удалить аватар: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(PhoneNumberAlreadyTakenException.class)
    public ResponseEntity<String> PhoneNumberAlreadyTakenException(PhoneNumberAlreadyTakenException ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
