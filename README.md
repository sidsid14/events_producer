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

## Environment Variables
SSL_LOCATION=/Users/kafka/ssl;SSL_PASS=kafka@password

## SSL Configuration
1. Generate Keystore
   - keytool -keystore server.keystore.jks -alias localhost -validity 365 -genkey -keyalg RSA -storetype pkcs12
2. Validate keystore
   - keytool -list -v -keystore server.keystore.jks
3. Generate CA
   - openssl req -new -x509 -keyout ca-key -out ca-cert -days 365 -subj "/CN=local-security-CA"
4. Certificate Signing Request (CSR)
   - keytool -keystore server.keystore.jks -alias localhost -certreq -file cert-file
5. Signing the certificate
   - openssl x509 -req -CA ca-cert -CAkey ca-key -in cert-file -out cert-signed -days 365 -CAcreateserial -passin pass:password
6. Add the signed cert in to KeyStore file
   - keytool -keystore server.keystore.jks -alias CARoot -import -file ca-cert
   - keytool -keystore server.keystore.jks -alias localhost -import -file cert-signed
7. Generate the TrustStore
   - keytool -keystore client.truststore.jks -alias CARoot -import -file ca-cert

## SSL Broker configuration
1. Create a file client-ssl.properties
```properties
     security.protocol=SSL
     ssl.truststore.location=<LOCATION>/client.truststore.jks
     ssl.truststore.password=<PASSWORD>
     ssl.truststore.type=JKS
     ssl.keystore.type=JKS
     ssl.keystore.location=<LOCATION>/client.keystore.jks
     ssl.keystore.password=<PASSWORD>
     ssl.key.password=<PASSWORD>
```

2. Broker configuration for server.properties
```properties
   listeners=PLAINTEXT://localhost:9092, SSL://localhost:9095
   auto.create.topics.enable=false
   ssl.keystore.location=<LOCATION>/server.keystore.jks
   ssl.keystore.password=<PASSWORD>
   ssl.key.password=<PASSWORD>
   ssl.endpoint.identification.algorithm=
   ssl.truststore.location=<LOCATION>/server.truststore.jks
   ssl.truststore.password=<PASSWORD>
   ssl.client.auth=required
   log.dirs=/tmp/kafka-logs
```

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

## References

 - [Kafka/Springboot Config Guide](https://github.com/dilipsundarraj1/kafka-for-developers-using-spring-boot)
 - [Apache Kafka Documentation](https://kafka.apache.org/documentation/#security_ssl)
 - [Spring Kafka Documentation](https://docs.spring.io/spring-kafka/reference/html)
 - [Certificate](https://www.udemy.com/certificate/UC-08909b26-768c-4bf4-a4e0-744443e09903)


## Authors

- [@sudhanshu](https://www.github.com/sidsid14)

