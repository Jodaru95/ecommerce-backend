package com.josedavid.ecommerce.app.infraestructure.config;

import com.josedavid.ecommerce.app.domain.enums.Role;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@ecommerce.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
            System.out.println("Admin creado");
        }

        if (userRepository.findByUsername("user").isEmpty()) {

            User user = new User();
            user.setUsername("user");
            user.setEmail("user@ecommerce.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole(Role.USER);

            userRepository.save(user);
            System.out.println("User creado");
        }
    }
}
