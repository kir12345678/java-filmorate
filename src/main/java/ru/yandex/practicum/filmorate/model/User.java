package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class User {
    private int id;

    @NotBlank(message = "Электронная почта не может быть пустой!")
    @Email(message = "Некорректная эл. почта!")
    private String email;

    @NotBlank(message = "Логин не может быть пустым!")
    private String login;

    private String name;

    @DateTimeFormat(pattern = "dd.MM.yyyy")

    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
}
