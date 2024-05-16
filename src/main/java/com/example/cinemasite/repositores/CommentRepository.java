package com.example.cinemasite.repositores;

import com.example.cinemasite.models.Comment;
import com.example.cinemasite.models.Films;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByFilm(Films film);
}
