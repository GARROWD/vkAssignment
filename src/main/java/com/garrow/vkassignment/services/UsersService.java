package com.garrow.vkassignment.services;

import com.garrow.vkassignment.dto.UserPayload;
import com.garrow.vkassignment.models.Album;
import com.garrow.vkassignment.models.Post;
import com.garrow.vkassignment.models.User;
import com.garrow.vkassignment.repositories.UsersRepository;
import com.garrow.vkassignment.utils.exceptions.NotFoundException;
import com.garrow.vkassignment.utils.validators.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UsersService {
    // Repositories
    private final UsersRepository usersRepository;

    // Services
    private final ExceptionMessagesService exceptionMessages;

    // Utils
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Cacheable(value = "usersCache", key = "#id")

    public User getById(Long id)
            throws NotFoundException {
        return usersRepository.getById(id);
    }

    @Cacheable(value = "usersCache", key = "#id")
    public void existsById(Long id)
            throws NotFoundException {
        usersRepository.existsById(id);
    }

    public Album getAlbumsById(Long id) {
        return usersRepository.getAlbumsById(id);
    }

    public Post getPostsById(Long id) {
        return usersRepository.getPostsById(id);
    }


    @Cacheable(value = "usersCache", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<UserPayload.Request> getAll(Pageable pageable) {
        return usersRepository.getAll(pageable);
    }

    @CachePut(value = "usersCache", key = "#id")
    @Transactional
    public User create(User user) {
        validator.validate(user);
        usersRepository.create(user);

        log.info("User with ID {} is created", user.getId());

        return user;
    }

    @CachePut(value = "usersCache", key = "#id")
    @Transactional
    public User update(User unsavedUser, Long id) {
        User user = User.builder().build();
        modelMapper.map(getById(id), user);
        modelMapper.map(unsavedUser, user);
        validator.validate(user);
        usersRepository.create(user);

        log.info("User with ID {} is updated", user.getId());

        return user;
    }

    @CacheEvict(value = "usersCache", key = "#id")
    @Transactional
    public void deleteById(Long id)
            throws NotFoundException {
        existsById(id);
        usersRepository.deleteById(id);

        log.info("User with ID {} is deleted", id);
    }
}
