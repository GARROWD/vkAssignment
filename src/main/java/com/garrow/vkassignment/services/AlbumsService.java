package com.garrow.vkassignment.services;

import com.garrow.vkassignment.dto.PostPayload;
import com.garrow.vkassignment.models.Album;
import com.garrow.vkassignment.models.Photo;
import com.garrow.vkassignment.models.Post;
import com.garrow.vkassignment.models.User;
import com.garrow.vkassignment.repositories.AlbumsRepository;
import com.garrow.vkassignment.repositories.PostsRepository;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumsService {
    // Repositories
    private final AlbumsRepository albumRepository;

    // Utils
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Cacheable(value = "albumsCache", key = "#id")
    public Album getById(Long id)
            throws NotFoundException {
        return albumRepository.getById(id);
    }

    @Cacheable(value = "albumsCache", key = "#id")
    public void existsById(Long id)
            throws NotFoundException {
        albumRepository.getById(id);
    }

    /* TODO Конечно в Page нет поля totalElements, потому что в jsonplaceholder нет эндпоинта позволяющего узнать
        количество постов, хоть в документации и сказано, что постов всего 100 */
    @Cacheable(value = "albumsCache", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Album> getAll(Pageable pageable) {
        return albumRepository.getAll(pageable);
    }

    private User getUserFromContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
