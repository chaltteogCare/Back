package com.Chaltteok.DailyCheck;

import com.Chaltteok.DailyCheck.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DailyCheckApplication implements CommandLineRunner {

	@Autowired
	private JwtService jwtService;

	public static void main(String[] args) {
		SpringApplication.run(DailyCheckApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		jwtService.printJwtConfig();
	}
}
