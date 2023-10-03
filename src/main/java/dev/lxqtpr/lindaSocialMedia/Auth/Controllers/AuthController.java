package dev.lxqtpr.lindaSocialMedia.Auth.Controllers;

import dev.lxqtpr.lindaSocialMedia.Auth.Service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/token")
    public String token(Authentication authentication) {

        return tokenService.generateToken(authentication);
    }

    // for tests
    @GetMapping("/greeting")
    public String getGreetingFromToken(JwtAuthenticationToken token) {

        return "Hello, "+token.getName()+", your ID is: "+token.getToken().getId()+"!";
    }
}
