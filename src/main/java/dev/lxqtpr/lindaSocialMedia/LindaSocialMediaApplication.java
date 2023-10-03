package dev.lxqtpr.lindaSocialMedia;

import dev.lxqtpr.lindaSocialMedia.Auth.Configs.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
@EnableConfigurationProperties(RsaKeyProperties.class)
public class LindaSocialMediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LindaSocialMediaApplication.class, args);
	}

}
