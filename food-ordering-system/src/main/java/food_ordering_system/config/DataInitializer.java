package food_ordering_system.config;

import food_ordering_system.entity.Role;
import food_ordering_system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Runs on startup and ensures ADMIN and CUSTOMER roles exist.
// Safe to run multiple times — only inserts if the role is missing.
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(Role.builder().name("ADMIN").build());
        }
        if (roleRepository.findByName("CUSTOMER").isEmpty()) {
            roleRepository.save(Role.builder().name("CUSTOMER").build());
        }
    }
}