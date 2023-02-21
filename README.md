# Rest API testing using Rest-Assured Java Framework

- This is repository of basic REST API testing framework
- The public API used for this example is - https://petstore.swagger.io/#/
- This framework is developed using Rest-Assured library for Rest API testing

# Technologies/Tools used in building the framework

- Rest Assured
- TestNG
- Java
- JMeter plugin

# Framework implements below best practices

- Scalable and extensible
- Reusable Rest Assured specifications
- Reusable Rest Assured API requests
- Separation of API layer from test layer
- Support parallel execution

# About the Project Structure

src/main/java

Inclide the follow sub-packages:
- constants
- dtos - for serialization/deserialization of Request adn Response of an API method
- factories - responsible for creating Request objects located in dtos package.
- enums
- stpes - sequence of repeatable actions for an API method
- helpres - all common method 

src/test
- base
- tests
