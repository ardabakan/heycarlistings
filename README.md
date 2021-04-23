# Heycar Listings Challenge

This is a challenge implementation for Heycar's backend challenge

## Swagger API

Visit http://localhost:8080/swagger-ui.html/ for API details
and to try the endpoints out by sample data

# Building and Running
## With Maven

Use the mvnw included in the root folder or your local mvn tool  [mvn](https://maven.apache.org/) to clean and install the application.

```bash
$ ./mvnw clean install
$ ./mvnw spring-boot:run
```

## With Docker

```bash
$ docker build --tag=heycarlistings:latest .
$ docker run -p8080:8080 heycarlistings:latest

```

## Tests
Tests related to the listings microservice will be 
automatically run during installation commands
