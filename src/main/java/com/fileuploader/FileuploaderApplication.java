package com.fileuploader;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan("com.fileuploader.controller")
@EntityScan
public class FileuploaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileuploaderApplication.class);
	System.out.println("Project has been started...");
	
	}

}

