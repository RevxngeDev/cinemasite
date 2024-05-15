package com.example.cinemasite.dto;

import com.example.cinemasite.models.Films;
import com.example.cinemasite.models.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmsDto {
    private Long id;
    private String name;
    private String picture;
    private Type type;
    private String overView;

    public static FilmsDto of(Films films){
        return FilmsDto.builder()
                .id(films.getId())
                .name(films.getName())
                .picture(films.getPicture())
                .type(films.getType())
                .overView(films.getOverView())
                .build();
    }
    public static List<FilmsDto> from(List<Films> films){
        return films.stream()
                .map(FilmsDto::of)
                .collect(Collectors.toList());
    }
}
