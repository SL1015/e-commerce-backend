# E-commerce-backend
This repository contains an implementation of a microservices e-commerce backend with Spring boot.
## Tech stack
Spring boot 3, OAuth2(KeyCloak), Eureka, Spring cloud gateway, Kafka, MySQL, PostgreSQL, Docker.
## Dependencies
Spring Boot 3  
Maven  
Java 17  
Docker
## Run the application with Docker
1. Run <code>mvn clean package -DskipTests</code> to build the applications.  
2. Run <code>docker-compose up -d</code> to start the applications.
## Ports
Gateway: 8180  
Eureka: 8761  
Key Cloak: 8080
## Access to the OAuth 2.0 authorization
1.	Add a line <code>127.0.0.1 keycloak</code> to the **hosts** file as administrator  
For **Windows** users, the file is in <code>C:\Windows\System32\drivers\etc</code>;   
For **Linux and Mac** users, the file is in <code>/etc/</code>.  
2.	Login to the Key Cloak server at port 8080 with **username** admin and **password** petcove.  
3.	Get the client secret in <code>petcove-microservices-real/Clients/petcove-client/Credentials</code>.
