package com.travel.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.travel.entity.Role;
import com.travel.entity.Usuario;
import com.travel.repository.RoleRepository;
import com.travel.repository.UsuarioRepository;

@Component
public class DataInitializer {
    @Bean
    public CommandLineRunner loadData(UsuarioRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            
            try {
                Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
                Role userRole = roleRepository.save(new Role("ROLE_USER"));

                Usuario admin = new Usuario("admin", "admin", "admin@travel.com", "{noop}admin123", Set.of(adminRole));
                Usuario user = new Usuario("user", "user", "user@travel.com", "{noop}user123", Set.of(userRole));

                userRepository.save(admin);
                userRepository.save(user);
            } catch(Exception e) {
                System.out.println("Initial data already exist.");
            }
        };
    }
}
