package org.glsid.AuthenticationService;

import org.glsid.AuthenticationService.entities.AppRole;
import org.glsid.AuthenticationService.entities.AppUser;
import org.glsid.AuthenticationService.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AuthenticationServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(AccountService accountService) {
        return args -> {
            accountService.addNewRole(new AppRole(null, "USER"));
            accountService.addNewRole(new AppRole(null, "ADMIN"));
            accountService.addNewRole(new AppRole(null, "CUSTOMER_MANAGER"));
            accountService.addNewRole(new AppRole(null, "PRODUCT_MANAGER"));
            accountService.addNewRole(new AppRole(null, "BILLS_MANAGER"));

            accountService.addNewUser(new AppUser(null, "Hammadi", "010203", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "admin", "1234567", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "user1", "1234", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "user2", "123456", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "user3", "010509", new ArrayList<>()));

            accountService.addRoleToUser("Hammadi", "USER");
            accountService.addRoleToUser("admin", "USER");
            accountService.addRoleToUser("admin", "ADMIN");
            accountService.addRoleToUser("user1", "USER");
            accountService.addRoleToUser("user1", "CUSTOMER_MANAGER");
            accountService.addRoleToUser("user2", "USER");
            accountService.addRoleToUser("user2", "PRODUCT_MANAGER");
            accountService.addRoleToUser("user3", "USER");
            accountService.addRoleToUser("user3", "BILLS_MANAGER");
        };
    }

}
