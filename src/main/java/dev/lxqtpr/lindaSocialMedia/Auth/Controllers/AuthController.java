package dev.lxqtpr.lindaSocialMedia.Auth.Controllers;

import dev.lxqtpr.lindaSocialMedia.Auth.Dto.TokensDto;
import dev.lxqtpr.lindaSocialMedia.Auth.Service.TokenService;
import dev.lxqtpr.lindaSocialMedia.Domain.User.Dto.UserDefaultCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public TokensDto login(@RequestBody UserDefaultCreationDto auth) {
        return tokenService.login(auth);
    }

    @PostMapping("/refresh-token")
    public String refreshToken(@RequestBody UserDefaultCreationDto auth) {
        return tokenService.generateRefreshToken(auth);
    }

    @PostMapping("/access-token")
    public String accessToken(JwtAuthenticationToken refreshToken) {
        return tokenService.generateAccessToken(refreshToken);
    }

    // for tests
    @GetMapping("/greeting")
    public String getGreetingFromToken(JwtAuthenticationToken token) {
        TokenService.checkAccessToken(token);
        return "Hello, "+token.getName()+", your ID is: "+token.getToken().getId()+"!";
    }
}