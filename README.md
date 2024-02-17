Only for learning purpose.

> [!WARNING]
> - Must do code formatting.
> - Remove or dont add extra lines or spaces.
> - Use better naming convention.
> - Write unit test cases using mockito.
> - Link issue with pull request.
> - Add screenshot to pull request.


## How to login into the application:

Login process written here: https://github.com/learning-project-01/bookstore-app/issues/71

## Postman Test Script To Set Env Variable From Response

```javascript 
var responseBody = pm.response.json();

// Extract the value field
var value = responseBody.value;

// Set the value as an environment variable
pm.environment.set("X-AUTH-TOKEN", value);

// Log the environment variable for verification
console.log("Environment variable set:", pm.environment.get("X-AUTH-TOKEN"));
```

## Github Actions
https://docs.github.com/en/actions/using-workflows/workflow-syntax-for-github-actions

https://docs.github.com/en/actions/automating-builds-and-tests/building-and-testing-java-with-maven

https://medium.com/@alexander.volminger/ci-cd-for-java-maven-using-github-actions-d009a7cb4b8f

https://dev.to/cicirello/using-github-actions-to-build-a-java-project-with-pull-request-coverage-commenting-and-coverage-badges-50a2

## Swagger Open API 3 Integration
- Added Swagger Spring Doc Dependency
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.0.3</version>
</dependency> 
```
- Added the property: `springdoc.enable-spring-security:false` in 
`application.properties` so that `SpringDocSecurityConfiguration` bean wont be created and search
for spring security related classes.
- The endpoint is accessible at: `http://localhost:8080/swagger-ui/index.html`
