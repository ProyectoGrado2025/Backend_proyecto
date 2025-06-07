package es.daw2.restaurant_V1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RestaurantV1Application {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantV1Application.class, args);
	}
}