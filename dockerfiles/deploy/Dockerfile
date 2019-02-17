FROM maven:3-jdk-11-slim AS COMPILATION_STAGE

WORKDIR /app

COPY pom.xml .
RUN mvn package -Dmaven.test.skip --fail-never

COPY . .
RUN mvn package -Dmaven.test.skip

FROM openjdk:11-jre-slim AS EXECUTION_STAGE

RUN groupadd -g 999 appuser \
  && useradd -r -u 999 -g appuser appuser

RUN mkdir /app \
  && chown 999:999 /app

WORKDIR /app

EXPOSE 8080

COPY --from=COMPILATION_STAGE /app/target/*.jar /app/app.jar

USER appuser

CMD ["java", "-jar", "app.jar"]
