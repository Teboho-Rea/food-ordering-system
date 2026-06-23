package food_ordering_system.repository;

import food_ordering_system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for the Menu entity.
// JpaRepository provides save, findById, findAll and delete out of the box.
public interface MenuRepository extends JpaRepository<Menu, Long> {
}