package dev.lxqtpr.lindaSocialMedia.Auth.Service;

import dev.lxqtpr.lindaSocialMedia.Domain.User.Service.UserService;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder encoder;

    private final UserService service;

    private final UserRepository repository;

    @Qualifier(value = "basicTimeZone")
    private final String timezone;

    private final UserDetailsManagerImpl detailsManager;

    public String generateToken(Authentication authentication) {
        System.out.println("Hello, "+authentication.getName()+"!");

        var user = repository.findUserEntityByUsername(authentication.getName()).get();

        //Instant now = Instant.now(Clock.system(ZoneId.of(timezone)));
        Instant now = Instant.now();

        var jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(180))
                .subject(authentication.getName())
                .id(user.getId().toString())
                .claim("roles", user.getAuthorities().stream().map(Object::toString).collect(Collectors.toSet()))
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

}
