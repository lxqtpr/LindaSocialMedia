package dev.lxqtpr.lindaSocialMedia.Domain.Role;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoleEnum implements GrantedAuthority {
    ROlE_ADMIN, ROLE_MODERATOR, ROLE_USER;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
