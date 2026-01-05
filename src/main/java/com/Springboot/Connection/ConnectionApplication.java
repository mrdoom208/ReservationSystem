package com.Springboot.Connection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ConnectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectionApplication.class, args);
	} 

}
    