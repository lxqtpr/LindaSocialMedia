package dev.lxqtpr.lindaSocialMedia.Auth.Service;

import dev.lxqtpr.lindaSocialMedia.Core.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisAuthService {

    @Qualifier(value = "redis-template")
    private final RedisTemplate<String, String> redis;

    public String setRefreshToken(String id, String refreshToken) {
        redis.opsForValue().set(id, refreshToken);
        return redis.opsForValue().get(id);
    }

    public boolean isKeyExists(String id) {
        return redis.opsForValue().get(id) != null;
    }

    public String getRefreshTokenById(String id) {
        StringBuilder val = new StringBuilder("");
        if (isKeyExists(id)) {
            val.append(redis.opsForValue().get(id));
        }
        if (val.equals(""))
            throw new ResourceNotFoundException("There is no refresh token with id="+id);

        return val.toString();
    }
}
