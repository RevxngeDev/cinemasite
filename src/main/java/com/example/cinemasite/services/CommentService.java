package com.example.cinemasite.services;

import com.example.cinemasite.models.Comment;
import com.example.cinemasite.models.Films;
import com.example.cinemasite.models.User;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentsByFilm(Films film);

    Comment addComment(Films film, User user, String text);

    void deleteComment(Long commentId, Long userId);

}
