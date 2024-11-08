package ru.yandex.practicum.filmorate.model;
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Integer> likes = new HashSet<>();
}
