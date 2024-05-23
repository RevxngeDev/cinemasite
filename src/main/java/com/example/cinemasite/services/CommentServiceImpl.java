package com.example.cinemasite.services;

import com.example.cinemasite.models.Comment;
import com.example.cinemasite.models.Films;
import com.example.cinemasite.models.User;
import com.example.cinemasite.repositores.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentsByFilm(Films film) {
        return commentRepository.findByFilm(film);
    }

    public Comment addComment(Films film, User user, String text) {
        Comment comment = new Comment();
        comment.setFilm(film);
        comment.setUser(user);
        comment.setText(text);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null && comment.getUser().getId().equals(userId)) {
            commentRepository.delete(comment);
        }
    }
}
