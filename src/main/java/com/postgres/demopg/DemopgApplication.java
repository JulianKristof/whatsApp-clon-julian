package com.postgres.demopg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.postgres.demopg.models.ERole;
import com.postgres.demopg.models.Role;
import com.postgres.demopg.repository.RoleRepository;

@SpringBootApplication
public class DemopgApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemopgApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {
        return args -> {
            // Revisamos si la tabla de roles está vacía
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(ERole.ROLE_USER));
                roleRepository.save(new Role(ERole.ROLE_ADMIN));
                roleRepository.save(new Role(ERole.ROLE_MODERATOR));
                
                System.out.println("--------------------------------------");
                System.out.println("✅ ÉXITO: Roles insertados correctamente");
                System.out.println("--------------------------------------");
            } else {
                System.out.println("--------------------------------------");
                System.out.println("ℹ️ INFO: Los roles ya existen en la DB");
                System.out.println("--------------------------------------");
            }
        };
    }
}
