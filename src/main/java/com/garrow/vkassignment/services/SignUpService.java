package com.garrow.vkassignment.services;

import com.garrow.vkassignment.models.User;
import com.garrow.vkassignment.models.entities.UserDetails;
import com.garrow.vkassignment.repositories.PostsRepository;
import com.garrow.vkassignment.repositories.jpa.UsersDetailsRepository;
import com.garrow.vkassignment.utils.enums.ExceptionMessages;
import com.garrow.vkassignment.utils.enums.Roles;
import com.garrow.vkassignment.utils.exceptions.generics.GenericException;
import com.garrow.vkassignment.utils.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignUpService {
    // Repositories
    private final UsersDetailsRepository usersDetailsRepository;

    // Services
    private final ExceptionMessagesService exceptionMessagesService;

    // Utils
    private final PasswordEncoder passwordEncoder;

    public UserDetails signUp(UserDetails user) {
        Optional<UserDetails> foundUserDetails = usersDetailsRepository.getByUsername(user.getUsername());
        if(foundUserDetails.isPresent()) {
            throw new GenericException(exceptionMessagesService.getMessage(ExceptionMessages.USER_ALREADY_EXISTS));
        }

        user.setRole(Roles.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersDetailsRepository.save(user);

        return user;
    }
}
