package dev.lxqtpr.lindaSocialMedia.Core.Configs.Time;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemTimeConfig {
    @Bean(name = "basicTimeZone")
    public String basicTimeZone() {
        return "GMT";
    }
}
