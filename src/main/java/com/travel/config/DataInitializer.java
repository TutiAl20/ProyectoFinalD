package com.travel.config;

import com.travel.entity.Role;
import com.travel.entity.UserEntity;
import com.travel.repository.RoleRepository;
import com.travel.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // Inyectamos el PasswordEncoder

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        createRoleIfNotExist("ADMIN");
        createRoleIfNotExist("USER");

        // Crear un usuario por defecto con el rol ADMIN
        createUserIfNotExist("admin@travel.com", "123456", "ADMIN", "Admin", "Admin");
    }

    private void createRoleIfNotExist(String roleName) {
        if (roleRepository.findByName(roleName) == null) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }
    private void createUserIfNotExist(String username, String password, String roleName, String nombre, String apellido) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            // Crear nuevo usuario
            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setNombre(nombre);
            user.setApellido(apellido);
            user.setFecha(LocalDate.now());

            // Obtener el rol desde la base de datos
            Role userRole = roleRepository.findByName(roleName);
            if (userRole != null) {
                // Asignamos el rol al usuario
                Set<Role> roles = new HashSet<>();
                roles.add(userRole);
                user.setRoles(roles);
            } else {
                // Si el rol no existe, podemos lanzar una excepción o hacer algún manejo adicional
                System.out.println("Rol no encontrado: " + roleName);
            }

            // Guardamos el usuario
            userRepository.save(user);
        } else {
            System.out.println("El usuario ya existe: " + username);  // Si el usuario ya existe
        }
    }

}