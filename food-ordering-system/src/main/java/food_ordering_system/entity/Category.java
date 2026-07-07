package food_ordering_system.entity;

import jakarta.persistence.*;
import lombok.Data;

// Represents the category table in the database.
@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;
}