Only for learning purpose.

## github Actions
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