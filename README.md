# Sales API
Esse projeto é uma API Restful de Controle de Vendas desenvolvido orientado a testes, com integração continua, cobertura de código, monitoramento, documentação, Content Negotiation, prática de versionamento de API, versionamento de base de dados, banco de dados em memória para testes, banco de dados relacional MySQL e configuração de CORS.

## Tools

:coffee: Java 8

:hammer_and_wrench: Intellij IDEA

:hammer_and_wrench: TDD

:hammer_and_wrench: BDD

:hammer_and_wrench: JUnit5

:hammer_and_wrench: Mockito

:hammer_and_wrench: Spring Boot

:hammer_and_wrench: Spring Data (JPA/Hibernate)

:hammer_and_wrench: H2 Database

:hammer_and_wrench: Spring Boot Admin

:hammer_and_wrench: Actuator

:hammer_and_wrench: Swagger

:hammer_and_wrench: Travis CI

:hammer_and_wrench: Jacoco

:hammer_and_wrench: Codecov

:hammer_and_wrench: MySQL

:hammer_and_wrench: Flyway

## Features

### Category

- [X] Save
- [X] Update
- [X] Delete
- [X] FindAllByProperties
- [X] FindById

### Provider

- [ ] Save
- [ ] Update
- [ ] Delete
- [ ] FindAllByProperties
- [ ] FindById

### Payment

- [ ] Save
- [ ] Update
- [ ] Delete
- [ ] FindAllByProperties
- [ ] FindById

### Salesman

- [ ] Save
- [ ] Update
- [ ] Delete
- [ ] FindAllByProperties
- [ ] FindById

### Client

- [ ] Save
- [ ] Update
- [ ] Delete
- [ ] FindAllByProperties
- [ ] FindById

### Provider

- [ ] Save
- [ ] Update
- [ ] Delete
- [ ] FindAllByProperties
- [ ] FindById

### Sale

- [ ] Save
- [ ] Update
- [ ] Delete
- [ ] FindAllByProperties
- [ ] FindById

## Run Spring Boot Admin

https://github.com/FelipeRBDantas/spring-boot-admin

## Run Generate Reports Jacoco

```mvnw test```

## Run Migrate Flyway

```mvn clean flyway:migrate```

## Database Configurations

Crie uma base de dados chamada "sales" e adicione no arquivo "application.properties" as configurações abaixo:

```
#Database
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/sales?useTimezone=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

## Usage

### Content Negotiation

Com o Content Negotiation, a aplicação se encontra orientada a Server-driven e Agent-driven, possibilitando que o cliente negocie a representação ou conteúdo de sua solicitação para JSON ou XML. Para negociar um determinado conteúdo, adicione em "Headers" a chave "Accept" com o valor "application/json" ou "application/xml".


[![Build Status](https://travis-ci.com/FelipeRBDantas/sales-api.svg?branch=master)](https://travis-ci.com/FelipeRBDantas/sales-api)
[![codecov](https://codecov.io/gh/FelipeRBDantas/sales-api/branch/master/graph/badge.svg)](https://codecov.io/gh/FelipeRBDantas/sales-api)
