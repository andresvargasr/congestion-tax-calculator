# Getting Started with congestion-tax-calculator 

This congestion-tax-calculator is a java spring boot application that makes easy the tax calculation 
via HTTP

## Prerequisites

JDK 17 or higher
Docker and docker compose (for bonus "Tax rules outside the app" section)

## How to run?

### Build
First step after cloning the repository should be building the maven project, so located in the java
folder (congestion-tax-calculator/java) type:
````shell
 ./mvnw clean install -U -s .mvn/wrapper/settings.xml
````

### Run as a spring boot app
Once the build is done successfully you can run the spring boot app:
````shell
 ./mvnw spring-boot:run
````
The application should start and will be accepting connections in the 8080 port.

### Check out the calculator in action

To test the application is working you can hit the HTTP GET endpoint:

````shell
curl --location --request GET 'http://localhost:8088/congestion-tax-calculator?vehicle=Car&dates=2013-03-04%2018:00:00&dates=2013-03-04%2018:30:00&dates=2013-01-15%2021:00:00'
````
For this case, as per the rules you should get "8" as a response



### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.2/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.2/reference/htmlsingle/index.html#web)
* [Spring Web Services](https://docs.spring.io/spring-boot/docs/3.2.2/reference/htmlsingle/index.html#io.webservices)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.2/reference/htmlsingle/index.html#using.devtools)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Producing a SOAP web service](https://spring.io/guides/gs/producing-web-service/)

