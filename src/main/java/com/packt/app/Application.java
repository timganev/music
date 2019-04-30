package com.packt.app;


import com.packt.app.logger.LoggerService;
import com.packt.app.logger.LoggerServiceImpl;
import org.mariadb.jdbc.internal.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//import com.packt.app.user.CarRepository;
//import com.packt.app.user.OwnerRepository;


@SpringBootApplication
public class Application {

	private LoggerService loggerService;

	@Autowired
	public Application(LoggerService loggerService) {
		this.loggerService = loggerService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	public void run(final String args[]) {
		loggerService.doStuff("value");
	}

}

