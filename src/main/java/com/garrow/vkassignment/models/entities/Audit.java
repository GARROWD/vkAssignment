package com.garrow.vkassignment.models.entities;

import com.garrow.vkassignment.utils.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String method;

    private String uri;

    private LocalDateTime localDateTime;

    private Roles role;

    private Boolean hasPermission;
}
