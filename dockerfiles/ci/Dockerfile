FROM maven:3-jdk-11-slim

WORKDIR /app

COPY pom.xml .

RUN mvn verify --fail-never
