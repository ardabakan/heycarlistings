# Heycar Listings Challenge

This is a challenge implementation for Heycar's backend challenge

## Swagger API

Visit http://localhost:8080/swagger-ui.html/ for API details
and to try the endpoints out by sample data

![resim](https://user-images.githubusercontent.com/23276113/116003028-a7c55080-a5fc-11eb-8049-76f42bd967c0.png)


# Building
### With Maven

Use the mvnw included in the root folder or your local mvn tool  [mvn](https://maven.apache.org/) to clean and install the application.

```bash
$ ./mvnw clean install
```

or

```bash
$ mvn clean install
```
# Running

### With Maven

```bash
$ ./mvnw spring-boot:run
```
or

```bash
$ mvn spring-boot:run
```
### With Docker

```bash
$ docker build --tag=heycarlistings:latest .
$ docker run -p8080:8080 heycarlistings:latest

```

## Tests
Tests related to the listings microservice will be 
automatically run during installation commands

#### Comments

###### Problems you discovered

- The need to unify listings from CSV and JSON (for instance HP and KW conversions)
- The need to apply JPA ExampleMatcher for searches

###### Executed tests and results

- Uploading a csv file and having a 200 response from server
- Searching for listings among the csv file contents and finding them successfully
- Adding new listings via REST endpoint and having a 200 response from server
- Searching for listings that we recently saved via JSON and finding them successfully
- Add a new CSV from same dealer, change some prices inside it, and add a new 6th listing
- Check that the new listing has also been added and the updated Audi's price has really changed

###### Ideas you would like to implement if you had time -explain how you would implement them

- Focus on CSV validation ( check file type, check column count etc)
- Implement authentication & authorization, fetch DealerId from Spring Boot user
- Unify the make/models and keep a global map which matches makes/models from every dealer
  (so that we do not have ambiguities like
  VW -> from dealer1 and
  VolksWagen -> from dealer2 )
- Also keep another db for the real colour codes, power options (HP/KW) so that wrong info from dealers just be overwritten
  by our system (for instance if a Mercedes B200 listing is sent with 20 HP, that's obviously wrong and thus needs correction
  from our side)

###### Decisions you had to take and why

- Use H2 db for sake of ease and less configuration
- Use of Swagger for API demonstration

###### Tested architectures and reasons why you chose them instead of other options
- Decided to use -> Spring Boot microservices, in memory db H2 and JPA layer for data manipulation & persistence
- Because -> state of the art tools for microservices development
