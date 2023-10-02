package dev.lxqtpr.lindaSocialMedia.Domain.User;

import lombok.Data;

// dto for user daily social media routine
@Data
public class UserUsualDto {
    private final Long id;
    private final String username;
    private final String name;
    private final String avatar;
    private final String city;
    private final String pageCover;
    private final String email;
    private final Boolean isVerified;
}
