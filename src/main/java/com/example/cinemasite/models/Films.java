package com.example.cinemasite.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "films")
public class Films {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String picture;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    private String overView;

    private String youtubeLink;

    private Double price;

    @JsonIgnore
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<FilmRating> ratings;

    @JsonIgnore
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
