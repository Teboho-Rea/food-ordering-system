package food_ordering_system.repository;

import food_ordering_system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// JpaSpecificationExecutor allows dynamic filtering with Specifications.
public interface MenuRepository extends JpaRepository<Menu, Long>,
        JpaSpecificationExecutor<Menu> {
}