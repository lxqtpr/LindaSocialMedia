package dev.lxqtpr.lindaSocialMedia.Auth.Service;

import dev.lxqtpr.lindaSocialMedia.Auth.Dto.TokensDto;
import dev.lxqtpr.lindaSocialMedia.Auth.Exceptions.IncorrectPasswordException;
import dev.lxqtpr.lindaSocialMedia.Auth.Exceptions.IncorrectTokenTypeException;
import dev.lxqtpr.lindaSocialMedia.Domain.User.Dto.UserDefaultCreationDto;
import dev.lxqtpr.lindaSocialMedia.Domain.User.Service.UserService;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder encoder;

    private final UserService service;

    private final UserRepository repository;

    private final RedisAuthService redisAuthService;

    @Qualifier(value = "basicTimeZone")
    private final String timezone;

    private final UserDetailsManagerImpl detailsManager;

    public TokensDto login(UserDefaultCreationDto auth) {
        var refresh = generateRefreshToken(auth);
        var access = generateAccessToken(auth);

        return new TokensDto(access, refresh);
    }

    private JwtClaimsSet claimAccessTokenClaims(UserEntity user) {
        Instant now = Instant.now();

        return JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(180))
                .subject(user.getUsername())
                .id(user.getId().toString())
                .claim("roles", user.getAuthorities().stream().map(Object::toString).collect(Collectors.toSet()))
                .claim("type", "access")
                .build();
    }

    private JwtClaimsSet claimRefreshTokenClaims(UserEntity user) {
        Instant now = Instant.now();

        return JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(30*24*3600))
                .subject(user.getUsername())
                .id(user.getId().toString())
                .claim("roles", user.getAuthorities().stream().map(Object::toString).collect(Collectors.toSet()))
                .claim("type", "refresh")
                .build();
    }

    private static void checkRefreshToken(JwtAuthenticationToken refreshToken) {
        var type = refreshToken.getTokenAttributes().get("type");
        if (!type.equals("refresh")) {
            throw new IncorrectTokenTypeException("Incorrect token type with type="+type);
        }
    }

    public static void checkAccessToken(JwtAuthenticationToken refreshToken) {
        var type = refreshToken.getTokenAttributes().get("type");
        if (!type.equals("access")) {
            throw new IncorrectTokenTypeException("Incorrect token type with type="+type);
        }
    }

    private String generateAccessToken(UserDefaultCreationDto auth) {
        var user = repository.findUserEntityByUsername(auth.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User with username '" +
                        auth.getUsername() + "' is not found!"));

        return this.encoder.encode(JwtEncoderParameters.from(
                claimAccessTokenClaims(user))).getTokenValue();
    }

    public String generateAccessToken(JwtAuthenticationToken refreshToken) {
        checkRefreshToken(refreshToken);
        var user = repository.findUserEntityByUsername(refreshToken.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User with username '" +
                        refreshToken.getName() + "' is not found!"));

        return this.encoder.encode(JwtEncoderParameters.from(
                claimAccessTokenClaims(user))).getTokenValue();
    }

    public String generateRefreshToken(UserDefaultCreationDto auth) {
        var user = repository.findUserEntityByUsername(auth.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User with username '"
                        + auth.getUsername() + "' is not found!"));
        if (!user.getPassword().equals(auth.getPassword()))
            throw new IncorrectPasswordException("Password is incorrect!");

        var refreshToken = this.encoder.encode(JwtEncoderParameters.from(
                claimRefreshTokenClaims(user))).getTokenValue();

        refreshToken = redisAuthService.setRefreshToken(user.getId().toString(), refreshToken);

        return refreshToken;
    }

}
