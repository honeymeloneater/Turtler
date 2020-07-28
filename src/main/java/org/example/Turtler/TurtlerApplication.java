package org.example.Turtler;

import io.swagger.models.Swagger;
import org.example.Turtler.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@Import(SwaggerConfiguration.class)
@SpringBootApplication
@EnableAsync
public class TurtlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurtlerApplication.class, args);
	}

}
