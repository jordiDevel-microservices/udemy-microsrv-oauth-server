package org.myself.udemy.springboot.app.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class SpringbootServicioOauthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioOauthServerApplication.class, args);
	}

}
