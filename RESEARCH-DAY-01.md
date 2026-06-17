### Day 01 Research

## Q1. What does CRUD stand for?

CRUD stands for Create, Read, Update, and Delete. These are the four basic operations used when working with data in an application and database.

## Q2. Difference between HTTP methods POST, PUT, PATCH, DELETE?

POST is used to create a new resource. It is non-idempotent. 
PUT is used to update an existing resource, usually by replacing the full resource. It is idempotent. 
PATCH is used to update only part of an existing resource.  It is typically non-idempotent.
DELETE is used to remove a resource. It is idempotent.

## Q3. Give the correct HTTP status code for each:

a. A new category was created: `201 Created`

b. A category was deleted successfully: `204 No Content`

c. The id requested does not exist: `404 Not Found`

d. The request body is missing a required field: `400 Bad Request`

e. The user is logged in but not allowed: `403 Forbidden`

## Q4. Difference between @RequestBody, @RequestParam, @PathVariable

`@RequestBody` gets data from the body of the request, usually JSON. It is used when sendingJSON/XML in POST/PUT/PATCH.

Example:
URL: POST /categories

Body:json{ "name": "Electronics", "description": "Gadgets" }

@PostMapping("/categories")
public Category create(@RequestBody CategoryDto dto) {
    // dto.name = "Electronics"
    return categoryService.save(dto);
}

`@RequestParam` gets data from the query string in the URL. It is used for filtering and searching items.

Example:
URL: GET /categories?page=2&size=10&sort=name

@GetMapping("/categories")
public List<Category> getAll(
@RequestParam int page,          // page = 2
@RequestParam int size,          // size = 10  
@RequestParam(required = false) String sort // sort = "name" or null
) {
return categoryService.findAll(page, size, sort);
}

`@PathVariable` gets data from the URL path. It is used when the value is part of the URL itself. For identifying which resource. 
Example:
URL: GET /categories/5Code

@GetMapping("/categories/{id}")
public Category getCategory(@PathVariable Long id) {
return categoryService.findById(id); // id = 5
}

## Q5. What is Jakarta Bean Validation? Explain @Valid, @NotBlank, @Size.
Jakarta Bean Validation is used to check if input data follows certain rules before the application processes it. It is the standard for validating Java objects.

`@Valid` tells Spring Boot to check the validation rules inside an object.

`@NotBlank` means the field cannot be empty, null or only spaces.

`@Size` limits the length of a value, for example making sure a name is between 2 and 50 characters.


## Q6. Why return a DTO and not the entity itself? Give 2 reasons.
A DTO hides the database structure from the client, so the API does not expose unnecessary or sensitive fields.
A DTO also gives the developer more control over what data is sent and received by the API.

## Q7. What is Optional<T>? Why does findById return Optional?
`Optional<T>` is a container that may or may not contain a value. It forces you to handle "not found" cases.
Why `findById` returns Optional because the record might exist, or it might not exist. 
This helps developers handle missing data properly instead of getting null errors.

### Self-Quiz

## Q1. Why ResponseEntity instead of returning the object?
ResponseEntity allows us to return both the response body and the HTTP status code. For example, when creating a category, we can return 201 Created instead of just returning the object.

## Q2. What status should a successful DELETE return? Why?
A successful DELETE should return 204 No Content. This means the record was deleted successfully, but there is no response body to return.

## Q3. Update only one field - PUT or PATCH? Defend your answer.
PATCH is better when updating only one field because it is meant for partial updates. PUT is usually used when replacing the whole object.

## Q4. What happens if you forget @Valid on the controller?
If @Valid is missing, Spring Boot will not check the validation annotations like @NotBlank and @Size. Bad input may be accepted even when it should be rejected.

## Q5. Why must update/delete have {id} in the URL but create does not?
Update and delete need {id} because the application must know which existing record to change or remove. Create does not need an id because the database creates a new id for the new record.