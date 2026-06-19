### Day 03 Research

## Q1. What is JPA? What is Hibernate? How are they related?
JPA (Java Persistence API) is a specification that defines how Java
objects should be mapped to database tables. Hibernate is the most
popular implementation of that specification. Spring Boot uses
Hibernate under the hood to actually execute the JPA annotations
we write, like @Entity and @Column.

## Q2. What is the difference between @Entity and @Table?
@Entity tells JPA that this class should be mapped to a database
table. @Table is optional and is used to customise details about
that table, such as giving it a specific name with @Table(name = "menus")
instead of using the default class name.

## Q3. What is a foreign key? What is @ManyToOne? Give 2 real-world examples.
A foreign key is a column that references the primary key of another
table, creating a relationship between two tables. @ManyToOne means
many rows in this table can relate to one row in another table.
Example 1: many Menu items belong to one Category.
Example 2: many Orders belong to one Customer.

## Q4. What does @JoinColumn(name = "category_id") do?
It tells JPA which column in the menus table should store the
foreign key value that links to the categories table. Without it,
JPA would generate a default column name on its own.

## Q5. Why store price as BigDecimal and not double?
double uses binary floating point which can introduce small rounding
errors when doing arithmetic. BigDecimal stores decimal numbers
exactly, which is essential when working with money so that totals
and calculations are always accurate to the cent.

## Q6. What does FetchType LAZY vs EAGER mean? What is the default for @ManyToOne?
LAZY means the related entity is only loaded from the database when
it is actually accessed in code. EAGER means it is loaded immediately
together with the parent entity. The default for @ManyToOne is EAGER.

## Q7. What is the N+1 query problem?
This happens when you fetch a list of N entities, and then for each
one a separate query is run to fetch a related entity, resulting in
1 query for the list plus N extra queries. This is a performance
problem because it can result in hundreds of unnecessary queries.

## Q8. What is dependency injection? Constructor injection vs field injection — which is preferred and why?
Dependency injection is when an object's dependencies are provided
to it from outside rather than the object creating them itself.
Constructor injection is preferred over field injection because it
makes dependencies explicit, allows fields to be final, and makes
the class easier to unit test.

## Q9. What does @RequiredArgsConstructor (Lombok) do?
It automatically generates a constructor that takes all the final
fields in the class as parameters. This removes the need to write
a constructor manually for dependency injection.

## Q10. What is the role of the SERVICE layer? Why must it be separate from the controller?
The service layer contains the business logic of the application,
such as validation rules and how data should be processed. It is
kept separate from the controller so the controller only handles
HTTP concerns, making the code easier to test, reuse and maintain.

## Q11. Why MUST you validate that categoryId exists before saving a menu?
If the categoryId does not exist in the database, saving a menu with
that id would either fail with a database error or create a broken
foreign key reference. Validating first lets us return a clear 404
error instead of a confusing database exception.

## Q12. Difference between save() and saveAndFlush()?
save() may delay writing the change to the database until the
transaction commits. saveAndFlush() immediately writes the change to
the database and forces a flush, which is useful when you need to
read the saved data again right away in the same transaction.

## Q13. Why write private mapper methods (entity <-> dto)?
Mapper methods centralise the logic of converting between an Entity
and a DTO in one place. This avoids duplicating the same mapping
code across multiple methods and makes it easy to update the mapping
in a single location if a field changes.