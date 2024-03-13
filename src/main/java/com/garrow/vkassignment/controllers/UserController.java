package com.garrow.vkassignment.controllers;

import com.garrow.vkassignment.dto.UserPayload;
import com.garrow.vkassignment.models.Album;
import com.garrow.vkassignment.models.Post;
import com.garrow.vkassignment.models.User;
import com.garrow.vkassignment.services.UsersService;
import com.garrow.vkassignment.utils.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    // Services
    private final UsersService usersService;

    // Utils
    private final Validator validator;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public Page<UserPayload.Request> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return usersService.getAll(PageRequest.of(page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public UserPayload.Request getUser(@PathVariable Long id) {
        return modelMapper.map(usersService.getById(id), UserPayload.Request.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/albums")
    public Album getAlbums(@PathVariable Long id) {
        return usersService.getAlbumsById(id); // TODO Payload
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/posts")
    public Post getPosts(@PathVariable Long id) {
        return usersService.getPostsById(id); // TODO Payload
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public UserPayload.Request createUser(@RequestBody UserPayload.Create userPayload) {
        validator.validate(userPayload);
        return modelMapper.map(usersService.create(modelMapper.map(userPayload, User.class)),
                               UserPayload.Request.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public UserPayload.Request updateUser(@PathVariable Long id, @RequestBody UserPayload.Update userPayload) {
        validator.validate(userPayload);
        return modelMapper.map(usersService.update(modelMapper.map(userPayload, User.class), id),
                               UserPayload.Request.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        usersService.deleteById(id);
    }
}

