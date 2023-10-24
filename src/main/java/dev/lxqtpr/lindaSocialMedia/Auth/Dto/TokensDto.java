package dev.lxqtpr.lindaSocialMedia.Auth.Dto;

import lombok.Data;

@Data
public class TokensDto {
    private final String access;
    private final String refresh;
}
