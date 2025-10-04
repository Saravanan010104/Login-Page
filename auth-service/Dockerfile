# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app
COPY target/auth-service-*.jar app.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","/app/app.jar"]
