package ru.skillsrock.user_api_service.util;

import ru.skillsrock.user_api_service.dto.UserDTO;
import ru.skillsrock.user_api_service.exception.IllegalPhoneNumberException;
import ru.skillsrock.user_api_service.exception.InvalidFIOException;
import ru.skillsrock.user_api_service.exception.NullArgumentException;

import java.util.regex.Pattern;

public class Validation {
    private static final Pattern NAME_REGEXP = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ\\s]+$");
    private static final Pattern PHONE_REGEXP = Pattern.compile("^\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}$");

    public static void validateUserDto(UserDTO userDTO) {
        if (userDTO == null) {
            throw new NullArgumentException("Создаваемый пользователь не может быть null");
        }
        validateName(userDTO.getFio());
        validatePhone(userDTO.getPhoneNumber());
    }

    private static void validateName(String fio) {
        if (fio == null || fio.isBlank()|| !NAME_REGEXP.matcher(fio).matches()) {
            throw new InvalidFIOException("Поле ФИО не может быть пустым и должно содержать только буквы");
        }
    }

    private static void validatePhone(String phone) {
        if (phone != null && !PHONE_REGEXP.matcher(phone).matches()) {
            throw new IllegalPhoneNumberException("Номер телефона должен соответствовать формату +7 XXX XXX-XX-XX");
        }
    }

}
