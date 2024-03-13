package com.garrow.vkassignment.repositories.jpa;

import com.garrow.vkassignment.models.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersDetailsRepository
        extends JpaRepository<UserDetails, String> {
    Optional<UserDetails> getByUsername(String username);
    Optional<UserDetails> findByUsernameAndIdIsNot(String username, Long id);
}
