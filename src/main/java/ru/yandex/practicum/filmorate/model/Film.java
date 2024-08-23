package ru.yandex.practicum.filmorate.model;
import lombok.Data;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {
    private int id;
    private String name;
    private String description;
    LocalDate releaseDate;
    int duration;
}
