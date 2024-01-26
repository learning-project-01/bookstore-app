Only for learning purpose.

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