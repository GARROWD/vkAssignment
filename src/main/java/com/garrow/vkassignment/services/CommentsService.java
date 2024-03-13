package com.garrow.vkassignment.services;

import com.garrow.vkassignment.dto.CommentPayload;
import com.garrow.vkassignment.models.Comment;
import com.garrow.vkassignment.models.User;
import com.garrow.vkassignment.repositories.CommentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentsService {
    // Repositories
    private final CommentsRepository commentsRepository;

    public Page<CommentPayload.Request> getByPostId(Long postId, Pageable pageable) {
        return commentsRepository.getByPostId(postId, pageable);
    }

    public Comment createByPostId(Long postId, Comment comment) {
        comment.setPostId(postId);
        comment.setName(getUserFromContext().getUsername());
        comment.setEmail(getUserFromContext().getEmail());
        return commentsRepository.create(postId, comment);
    }

    private User getUserFromContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
