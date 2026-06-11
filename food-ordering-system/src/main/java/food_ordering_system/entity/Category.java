package food_ordering_system.entity;

import jakarta.persistence.*;
import lombok.Data;

// This class represents the category table in the database.
@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}