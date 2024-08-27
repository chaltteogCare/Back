package com.Chaltteok.DailyCheck;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@OpenAPIDefinition(servers = {
//		@Server(url = "/", description = "Default Server URL")
//})
@SpringBootApplication
public class DailyCheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyCheckApplication.class, args);
	}

}
