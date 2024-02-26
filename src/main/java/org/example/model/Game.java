package org.example.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Game {
    private int id;
    private String name;
    private LocalDate releaseDate;
    private int rating;
    private double cost;
    private String description;
}
