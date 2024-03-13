package com.garrow.vkassignment.services;

import com.garrow.vkassignment.dto.PostPayload;
import com.garrow.vkassignment.models.Post;
import com.garrow.vkassignment.models.User;
import com.garrow.vkassignment.repositories.PostsRepository;
import com.garrow.vkassignment.utils.validators.Validator;
import com.garrow.vkassignment.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {
    // Repositories
    private final PostsRepository postsRepository;

    // Utils
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Cacheable(value = "postsCache", key = "#id")
    public Post getById(Long id)
            throws NotFoundException {
        return postsRepository.getById(id);
    }

    @Cacheable(value = "postsCache", key = "#id")
    public void existsById(Long id)
            throws NotFoundException {
        postsRepository.getById(id);
    }

    /* TODO Конечно в Page нет поля totalElements, потому что в jsonplaceholder нет эндпоинта позволяющего узнать
        количество постов, хоть в документации и сказано, что постов всего 100 */
    @Cacheable(value = "postsCache", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<PostPayload.Request> getAll(Pageable pageable) {
        return postsRepository.getAll(pageable);
    }

    @CachePut(value = "postsCache", key = "#id")
    public Post create(Post post) {
        validator.validate(post);
        post.setUserId(post.getUserId());
        Post createdPost = postsRepository.create(post);

        log.info("Post with ID {} is created", createdPost.getId());

        return createdPost;
    }

    @CachePut(value = "postsCache", key = "#id")
    public Post update(Post unsavedPost, Long id) {
        Post post = Post.builder().build();
        modelMapper.map(getById(id), post);
        modelMapper.map(unsavedPost, post);
        validator.validate(post);
        postsRepository.update(post);

        log.info("Post with ID {} is updated", post.getId());

        return post;
    }

    @CacheEvict(value = "postsCache", key = "#id")
    public void deleteById(Long id)
            throws NotFoundException {
        existsById(id);
        postsRepository.deleteById(id);

        log.info("Post with ID {} is deleted", id);
    }

    private User getUserFromContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
