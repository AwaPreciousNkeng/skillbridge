package com.codewithpcodes.skillbridge;

import com.codewithpcodes.skillbridge.auth.AuthenticationService;
import com.codewithpcodes.skillbridge.auth.RegisterRequest;
import com.codewithpcodes.skillbridge.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = new RegisterRequest(
                    "Admin",
                    "Admin",
                    "admin@gmail.com",
                    "password",
                    "",
                    Role.ADMIN
            );
            System.out.println("Admin token: " + service.register(admin).getAccessToken());
        };
    }
}
