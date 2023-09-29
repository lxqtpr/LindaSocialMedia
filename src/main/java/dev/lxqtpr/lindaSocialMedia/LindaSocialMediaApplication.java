package dev.lxqtpr.lindaSocialMedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class LindaSocialMediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LindaSocialMediaApplication.class, args);
	}

}
