package com.garrow.vkassignment.controllers;

import com.garrow.vkassignment.models.Album;
import com.garrow.vkassignment.models.Photo;
import com.garrow.vkassignment.services.AlbumsService;
import com.garrow.vkassignment.utils.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {
    // Services
    private final AlbumsService albumsService;

    // Utils
    private final Validator validator;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public Page<Album> getAllAlbums(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return albumsService.getAll(PageRequest.of(page, size)); // TODO Payload
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public Album getAlbums(@PathVariable Long id) {
        return albumsService.getById(id); // TODO Payload
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/photos")
    public Page<Photo> getPhotos(@PathVariable Long id) {
        return null; // TODO
    }
}

