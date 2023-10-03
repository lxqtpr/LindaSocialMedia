package dev.lxqtpr.lindaSocialMedia.Domain.User.Dto;

import dev.lxqtpr.lindaSocialMedia.Domain.Role.UserRoleEnum;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private final Long id;

    private final String username;
    private final String password;
    private final String name;
    private final String avatar;
    private final String city;
    private final String pageCover;
    private final String email;
    private final Set<UserRoleEnum> roles;
    private final Boolean isVerified;

    // for user details functionality
    private final Boolean isAccountNonExpired;
    private final Boolean isAccountNonLocked;
    private final Boolean isCredentialsNonExpired;
    private final Boolean isEnabled;
}
