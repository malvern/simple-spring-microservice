# Contact-Service
is a multi-module application

### Contact-Service-Spec
 defines specification for contact-service
 
### Contact-Service
- integrates with external services
- it uses spec defined in the contact-service-spec

## How to run the Service


`mvn clean install`

this will generate specification and model classes for *Contact-Service*

then: change directory into `contact-service` and run

`mvn spring-boot:run`

### Lazy way
Assuming you are on Mac or Linux

Run

`chmod -R 777 ./contact-service.sh`

then

`./contact-service.sh`

this two command will then start contact-service application.

## Service Swagger-UI

`http://localhost:9000/simple/search-api.html`

## Postman collection

Postman Collection can be found inside postman directory

## Docker
After running mvn clean

`mvn clean install`

run: 

`docker-compose up -d`

