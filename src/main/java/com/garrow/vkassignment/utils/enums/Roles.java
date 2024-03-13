package com.garrow.vkassignment.utils.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_ANONYMOUS,
    ROLE_USER,
    ROLE_POSTS_EDITOR,
    ROLE_POSTS_VIEWER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
