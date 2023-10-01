package dev.lxqtpr.lindaSocialMedia;

import dev.lxqtpr.lindaSocialMedia.Auth.Configs.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class LindaSocialMediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LindaSocialMediaApplication.class, args);
	}

}
