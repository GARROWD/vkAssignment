package com.garrow.vkassignment.repositories.jpa;

import com.garrow.vkassignment.models.entities.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository
        extends JpaRepository<Audit, String> {
}
