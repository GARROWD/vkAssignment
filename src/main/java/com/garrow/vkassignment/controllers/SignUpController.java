package com.garrow.vkassignment.controllers;

import com.garrow.vkassignment.dto.UserPayload;
import com.garrow.vkassignment.models.User;
import com.garrow.vkassignment.models.entities.UserDetails;
import com.garrow.vkassignment.services.SignUpService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sign-up")
@RequiredArgsConstructor
public class SignUpController {
    // Services
    private final SignUpService signUpService;

    // Utils
    private final ModelMapper modelMapper;

    @PostMapping
    public UserPayload.Request signUp(@RequestBody UserPayload.Create userPayload) {
        return modelMapper.map(signUpService.signUp(modelMapper.map(userPayload, UserDetails.class)),
                               UserPayload.Request.class);
    }
}
