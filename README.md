# Sales API
Esse projeto é uma API Restful de Controle de Vendas desenvolvido orientado a testes, com integração continua, cobertura de código, monitoramento, documentação, versionamento de base de dados, banco de dados em memória para testes e banco de dados relacional MySQL.

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

## Flyway

### Configurations

Adicionar ou descomentar no arquivo pom.xml a configuração do Flyway de plugin em build, como demonstrado logo abaixo:
```
<plugin>
	<groupId>org.flywaydb</groupId>
	<artifactId>flyway-maven-plugin</artifactId>
	<configuration>
	 <url>jdbc:mysql://localhost:3306/sales?useTimezone=true&amp;serverTimezone=UTC&amp;useSSL=false</url>
	 <user>root</user>
	 <password></password>
	</configuration>
	<dependencies>
	 <dependency>
	  <groupId>mysql</groupId>
	  <artifactId>mysql-connector-java</artifactId>
	  <version>${mysql.version}</version>
	 <exclusions>
	  <exclusion>
	    <groupId>slf4j-api</groupId>
	    <artifactId>org.slf4j</artifactId>
	   </exclusion>
	  </exclusions>
	 </dependency>
	</dependencies>
 </plugin>
```

### Run Migrate Flyway

```mvn clean flyway:migrate```

[![Build Status](https://travis-ci.com/FelipeRBDantas/sales-api.svg?branch=master)](https://travis-ci.com/FelipeRBDantas/sales-api)
[![codecov](https://codecov.io/gh/FelipeRBDantas/sales-api/branch/master/graph/badge.svg)](https://codecov.io/gh/FelipeRBDantas/sales-api)
