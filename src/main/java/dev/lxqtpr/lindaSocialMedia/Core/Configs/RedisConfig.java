package dev.lxqtpr.lindaSocialMedia.Core.Configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.support.collections.DefaultRedisMap;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean(name = "redis-template")
    @Primary
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();

        var connection = new JedisConnectionFactory();
        connection.afterPropertiesSet();

        template.setConnectionFactory(connection);

        return template;
    }
}
