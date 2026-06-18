### Day 02 Research

## Q1. What is a Java generic type? Why is <T> useful?
A generic type allows a class or method to work with any data type
without knowing it in advance. <T> is useful because it lets us
write one Response class that can wrap a CategoryDto, a List, or
any other type without duplicating code.

## Q2. What does Lombok @Builder generate behind the scenes?
@Builder generates a static inner Builder class with a method for
each field. You chain the methods together and call .build() at the
end to create the object. It removes the need to write a long
constructor call.

## Q3. What is the Builder design pattern? When to use it?
The Builder pattern is used to construct complex objects step by
step. It is useful when a class has many fields and you do not want
to pass them all in a constructor. It makes the code more readable.

## Q4. What is LocalDateTime? How is it different from Date?
LocalDateTime represents a date and time without a timezone.
Date is an older class that includes timezone information and has
many deprecated methods. LocalDateTime is part of the newer
java.time API and is much easier to work with.

## Q5. Why does a consistent response format matter to frontend developers?
A consistent format means the frontend always knows exactly where to
find the data, the status code and any error messages. This reduces
bugs and makes it easier to build the UI without checking every
endpoint individually.

## Q6. What does @JsonInclude(JsonInclude.Include.NON_NULL) do?
It tells Jackson not to include fields that are null in the JSON
response. For example if the data field is null on an error response
it will be left out of the JSON entirely.

## Q7. What is a static factory method? Why use Response.success() instead of new Response<>()?
A static factory method is a static method that creates and returns
an object. Response.success() is clearer to read than new Response<>()
because the method name describes what kind of response is being
created. It also hides the construction details.

### Self-Quiz

## Q1. Why use generic <T> instead of Object for data field?
Using Object would require casting when reading the data back, which
can cause runtime errors. With <T> the compiler checks the type at
compile time and no casting is needed.

## Q2. Difference between Response<T> and ResponseEntity<T>?
Response<T> is our custom wrapper that holds statusCode, message,
data and timestamp inside the JSON body. ResponseEntity<T> is a
Spring class that controls the HTTP status code and headers at the
HTTP level. You can use both together so the HTTP status and the
body status match.

## Q3. If a request fails, what statusCode does Response hold?
The Response will hold the relevant HTTP error code such as 404 for
not found or 400 for a bad request.

## Q4. Why add a timestamp?
A timestamp lets developers and support teams know exactly when a
response was generated. This is useful for debugging and for logging
purposes.