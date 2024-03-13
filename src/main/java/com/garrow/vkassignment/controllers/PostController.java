package com.garrow.vkassignment.controllers;

import com.garrow.vkassignment.dto.CommentPayload;
import com.garrow.vkassignment.dto.PostPayload;
import com.garrow.vkassignment.models.Comment;
import com.garrow.vkassignment.models.Post;
import com.garrow.vkassignment.services.CommentsService;
import com.garrow.vkassignment.services.PostsService;
import com.garrow.vkassignment.utils.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    // Services
    private final PostsService postsService;
    private final CommentsService commentsService;

    // Utils
    private final Validator validator;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('ADMIN', 'POSTS_VIEWER', 'POSTS_EDITOR')")
    @GetMapping
    public Page<PostPayload.Request> getAllPosts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return postsService.getAll(PageRequest.of(page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'POSTS_VIEWER', 'POSTS_EDITOR')")
    @GetMapping("/{id}")
    public PostPayload.Request getPost(@PathVariable Long id) {
        return modelMapper.map(postsService.getById(id), PostPayload.Request.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'POSTS_VIEWER', 'POSTS_EDITOR')")
    @GetMapping("/{id}/comments")
    public Page<CommentPayload.Request> getPostComments(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return commentsService.getByPostId(id, PageRequest.of(page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'POSTS_VIEWER', 'POSTS_EDITOR')")
    @PostMapping("/{id}/comments")
    public CommentPayload.Request createPostComments(
            @PathVariable Long id, @RequestBody CommentPayload.Create commentPayload) {
        return modelMapper.map(commentsService.createByPostId(id, modelMapper.map(commentPayload, Comment.class)),
                               CommentPayload.Request.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'POSTS_EDITOR')")
    @PostMapping
    public PostPayload.Request createPost(@RequestBody PostPayload.Create postPayload) {
        validator.validate(postPayload);
        return modelMapper.map(postsService.create(modelMapper.map(postPayload, Post.class)),
                               PostPayload.Request.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'POSTS_EDITOR')")
    @PutMapping("/{id}")
    public PostPayload.Request updatePost(@PathVariable Long id, @RequestBody PostPayload.Update postPayload) {
        validator.validate(postPayload);
        return modelMapper.map(postsService.update(modelMapper.map(postPayload, Post.class), id),
                               PostPayload.Request.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'POSTS_EDITOR')")
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postsService.deleteById(id);
    }
}

