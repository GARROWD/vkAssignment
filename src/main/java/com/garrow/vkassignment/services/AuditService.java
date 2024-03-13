package com.garrow.vkassignment.services;

import com.garrow.vkassignment.models.User;
import com.garrow.vkassignment.models.entities.Audit;
import com.garrow.vkassignment.repositories.jpa.AuditRepository;
import com.garrow.vkassignment.utils.enums.Roles;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
@Slf4j
public class AuditService {
    private final AuditRepository auditRepository;

    @EventListener
    public <T> void event(AuthorizationEvent event) {
        Audit audit = Audit.builder().build();

        try {
            User user = (User) (event.getAuthentication().get().getPrincipal());
            audit.setUsername(user.getUsername());
            audit.setRole(user.getRole());
        } catch(ClassCastException e) {
            audit.setUsername("anonymous");
            audit.setRole(Roles.ROLE_ANONYMOUS);
        }

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        audit.setMethod(request.getMethod());
        audit.setUri(request.getRequestURI());
        audit.setLocalDateTime(LocalDateTime.now());
        audit.setHasPermission(event.getAuthorizationDecision().isGranted());

        auditRepository.save(audit);
        log.info(audit.toString());
    }
}