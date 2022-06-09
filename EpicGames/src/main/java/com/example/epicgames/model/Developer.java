package com.example.epicgames.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "developers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String info;

    @ManyToMany(mappedBy = "developers")
    private List<Game> games;

}
