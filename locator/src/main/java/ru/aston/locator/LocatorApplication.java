package ru.aston.locator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocatorApplication.class, args);
	}

}
