package food_ordering_system.repository;

import food_ordering_system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// Repository for the Menu entity.
// JpaSpecificationExecutor enables dynamic filtering with Specifications.
public interface MenuRepository extends JpaRepository<Menu, Long>,
        JpaSpecificationExecutor<Menu> {

// Repository for the Menu entity.
// JpaRepository provides save, findById, findAll and delete out of the box.
public interface MenuRepository extends JpaRepository<Menu, Long> {
}