# Kafka events producer

The project is built using springboot framework. The project should expose APIs to receive application/json data and publish them using Kafka producer.



## Features

- Best practices for REST Apis with unit testing using JUnit and Mockito.
- Multiple approaches for producing data - sync and async.
- Using both approaches, first using a default topic described in application.yml and second explicit topic description while publishing data. 
- Application.yml includes configuration for different environments.
- Using embedded Kafka for testing code.
- Includes Integration/Unit tests for the code.


## Application Architecture

![App](https://user-images.githubusercontent.com/27942487/167331116-635e404a-202b-46ea-aeca-e5a4ce3d02d1.png)
![API ref](https://user-images.githubusercontent.com/27942487/167331174-66da808d-7b8c-4cfd-b0a8-783c8ba6663c.png)


## Plugins 

    1. Spring Web
        - Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.
    2. Spring for Apache Kafka
        - Publish, subscribe, store, and process streams of records.
    3. Lombok DEVELOPER TOOLS
    4. Validation
        - Bean Validation with Hibernate validator.


## API Reference

#### Post event async

```http
  POST /v1/libraryevent
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `application/json` | <pre>{<br> "libraryEventId":null,<br> "book":{ <br>&emsp; "bookId":451, <br>&emsp; "bookName":"Kafka Using Spring Boot", <br>&emsp; "bookAuthor":"Sudhanshu" <br>&emsp; } <br> } </pre>|


#### Post event sync

```http
  POST /v1/libraryevent-sync
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `application/json` | <pre>{<br> "libraryEventId":null,<br> "book":{ <br>&emsp; "bookId":451, <br>&emsp; "bookName":"Kafka Using Spring Boot", <br>&emsp; "bookAuthor":"Sudhanshu" <br>&emsp; } <br> } </pre>|




## Authors

- [@sudhanshu](https://www.github.com/sidsid14)

