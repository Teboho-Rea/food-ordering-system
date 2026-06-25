# food-ordering-system

PART 4: PROJECT INVESTIGATION

1. What is Spring Boot?
Spring Boot is a Java framework that helps developers quickly develop Java applications. 
It can be used to create many different types of applications, but it is primarily used for web applications and creating REST APIs. 
Spring Boot itself is a framework built on top of the Spring Framework.

2. What is Maven?
Maven is a dependency management and build tool for Java projects. 
It can automatically download libraries your project needs, compile your code, run tests, and package your project. 

3. What is the purpose of pom.xml?
pom.xml is Maven’s core project configuration file. 
This file contains information about your project, dependencies, plugins, build configuration, and project version information.

4. What is the purpose of application.properties?
application.properties This file is used to configure your Spring Boot application. 
For example, you can define the port your server should run on, configure database connections, define logging levels, and set values you want to use in your application.

5. What does @SpringBootApplication do?
@SpringApplicationConfiguration is used to designate the main application class of a Spring Boot application. 
Inside Spring is configuration information that allows Spring Boot to start up and automatically wire your application.

6. Why do developers use dependency management tools such as Maven? 
Dependency managers like Maven allow developers to automatically download and setup any external libraries their application uses. 
The Dependency Manager also knows what version to use and automatically downloads any transitive Dependencies. 
This allows developers to build projects that are easier to share with others and ensure everyone has the exact same configuration on their machines.

7. What is a REST API?
REST API REST stands for REpresentational State Transfer. REST APIs allow applications to communicate over HTTP using requests. 
These requests are built using standardized methods such as GET, POST, PUT, and DELETE. 
REST APIs typically expose data at specific URLs and send responses in JSON format.

8. What is JSON?
JSON (JavaScript Object Notation) is a lightweight text-based format for storing and sending data. 
JSON is made up of key value pairs. It is commonly used when sending data through REST APIs.

9. What is Dependency Injection?
Dependency Injection is a pattern where an object receives its dependencies from an external source rather than create the dependencies yourself. 
Spring Framework automatically provides dependencies to your classes. 
This allows your code to be more testable, reusable, and maintainable.

PART 5: PACKAGE STRUCTURE

1. Controller 
Handles incoming HTTP requests from the client, such as a browser, Postman, or frontend app. 
It receives requests like GET, POST, PUT, and DELETE, and then calls the service layer.

2. Service  
Holds the main business logic of the application. 
It determines what should happen when a request is made, such as checking rules, processing data, and calling the repository.

3. Repository  
Interacts with the database. 
It is used to save, find, update, and delete records, usually by extending Spring Data JPA interfaces.

4. Entity  
Includes classes that represent database tables. 
For example, a Customer entity usually corresponds to a customer table in the database.

5. DTO
DTO stands for Data Transfer Object. 
It is used to transfer data between the client and the application without exposing the entire entity or database structure.

6. Config  
Contains configuration classes for the application. 
This includes settings for security, CORS, Swagger/OpenAPI, custom beans, and other Spring setup.

7. Exception 
Includes custom error classes and error-handling logic. 
It helps the application return clear error messages when something goes wrong, like “Customer not found.”

## API Response Format

Every endpoint returns a consistent `Response<T>` wrapper containing
a status code, message, data, and timestamp.

### Success response example

```json
{
  "statusCode": 200,
  "message": "Category retrieved",
  "data": {
    "id": 11,
    "name": "Chicken",
    "description": "Chicken alfredo with pasta"
  },
  "timestamp": "2026-06-18T23:10:11.7184281"
}
```

### Success response with no data (e.g. DELETE)

```json
{
  "statusCode": 200,
  "message": "Category deleted",
  "timestamp": "2026-06-18T23:07:37.4362829"
}
```

The `data` field is omitted entirely when it has no value, using
`@JsonInclude(JsonInclude.Include.NON_NULL)`.

### Error response example

```json
{
  "statusCode": 404,
  "message": "Category not found with id: 1",
  "timestamp": "2026-06-18T23:08:39.0224635"
}
```
## Menu Endpoints

| Method | Path | Description | Query Params |
|--------|------|-------------|--------------|
| POST | /api/menu | Create a menu item | — |
| GET | /api/menu | List all menus | categoryId, search, page, size, sort |
| GET | /api/menu/{id} | Get one menu item | — |
| PUT | /api/menu/{id} | Update a menu item | — |
| DELETE | /api/menu/{id} | Delete a menu item | — |

### Example filtered request

```
GET /api/menu?categoryId=1&search=burger&page=0&size=5&sort=price,asc
```

### Example paginated response

```json
{
  "statusCode": 200,
  "message": "Menus retrieved",
  "data": {
    "content": [
      {
        "id": 1,
        "name": "Cheese Burger",
        "description": "Classic beef patty with cheese",
        "price": 49.99,
        "imageUrl": "https://placehold.co/300",
        "categoryId": 1,
        "categoryName": "Fast Food"
      }
    ],
    "totalElements": 4,
    "totalPages": 1,
    "number": 0,
    "size": 5,
    "first": true,
    "last": true
  },
  "timestamp": "2026-06-23T09:00:00"
}
```