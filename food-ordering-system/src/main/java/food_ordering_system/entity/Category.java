package food_ordering_system.entity;

import jakarta.persistence.*;
import lombok.Data;

// This class represents the category table in the database.
// Each instance maps to one row in that table.
@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;
}