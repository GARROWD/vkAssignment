package com.garrow.vkassignment.security;

import com.garrow.vkassignment.models.entities.UserDetails;
import com.garrow.vkassignment.repositories.jpa.UsersDetailsRepository;
import com.garrow.vkassignment.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl
        implements UserDetailsService {
    private final UsersDetailsRepository usersDetailsRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<UserDetails> foundUser = usersDetailsRepository.getByUsername(username);

        if(foundUser.isEmpty()) {
            throw new NotFoundException("");
        }
        UserDetails user = foundUser.get();

        return new UserDetailsImpl(user.getUsername(), user.getPassword(), List.of(user.getRole()));
    }
}
