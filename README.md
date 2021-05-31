## Supermarket Applicaton


### Description

Supermarket application to handle product inventory, payments, users and reports, each one of this exposed as a REST endpoint. The payment service is the one responsible for update the items on stock for each product, and it does that usign a RABBITMQ queue. The authorization filter to each endpoint is based on the user's role, CASHIER or ADMIN.


### Rest endpoints

Endpoint | Http Method | Authorized to 
------------ | ------------- | --------------
/api/v1/users/sign-up | POST | Permit All
/api/v1/products and /api/v1/products/{id} | GET | ROLE_CASHIER, ROLE_ADMIN
/api/v1/products | POST | ROLE_ADMIN
/api/v1/products/{id} | PUT | ROLE_ADMIN
/api/v1/products{id} | DELETE | ROLE_ADMIN
/api/v1/payments | GET | ROLE_ADMIN
/api/v1/payments{id} | GET | ADMIN
/api/v1/payments | POST | ROLE_CASHIER
/api/v1/reports | GET | ROLE_ADMIN


###  Steps to run application

1. Create a container for running MYSQL server:
```
    sudo docker run --name mysql -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_USER=springuser -e MYSQL_PASSWORD=1234 -e MYSQL_DATABASE=db_example -d mysql:latest
```
2. Get the IP address of the container:
```
    sudo docker inspect CONTAINER_ID;
```
3. Change the IP_ADDRESS in the application.properties file for the container's IP:
```
    spring.datasource.url=jdbc:mysql://IP_ADDRESS:3306/db_example
```
4. Create a container for RabbitMQ: 
```
    docker run -d  --name rabbitmq -p 15672:15672 -p 5672:5672 rabbitmq:3-management
```
5. Launch Application.

6. More info:
   * To test the requests on Postman, first execute the "Get User's token" and copy the TOKEN send by the server into the next header's requests.
   * Authorzation: Bearer TOKEN
   * Postman Collection: https://www.getpostman.com/collections/c6776f25fb1695aee988


### Techologies used

* Spring Boot
* Java 
* RabbitMQ
* JWT
* MySQL
* JPA 
* JUnit
* PostMan
