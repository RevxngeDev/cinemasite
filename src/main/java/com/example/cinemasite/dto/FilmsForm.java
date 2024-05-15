package com.example.cinemasite.dto;

import com.example.cinemasite.models.Type;
import lombok.Data;

import javax.persistence.*;

@Data
public class FilmsForm {
    private Long id;
    private String name;
    private String picture;
    private Type type;
    private String overView;

}
