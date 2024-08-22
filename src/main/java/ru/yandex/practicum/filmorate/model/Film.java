package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {
    private int id;
    @NotBlank(message = "Название не может быть пустым!")
    private String name;

    @Size(max = 200, message = "Максимальная длина описания — 200 символов!")
    private String description;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    //@MinimumDate
    LocalDate releaseDate;

    @Min(value = 0, message = "Продолжительность фильма должна быть положительной!")
    Duration duration;
}
