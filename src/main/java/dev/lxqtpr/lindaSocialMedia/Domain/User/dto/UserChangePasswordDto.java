package dev.lxqtpr.lindaSocialMedia.Domain.User.dto;

import lombok.Data;

@Data
public class UserChangePasswordDto {
    private final String username;
    private final String newPassword;
    private final String email;
}