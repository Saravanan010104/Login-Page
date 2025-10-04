# syntax=docker/dockerfile:1
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -B -ntp -DskipTests package -pl config-server -am

FROM eclipse-temurin:17-jre
ENV JAVA_OPTS=""
WORKDIR /app
COPY --from=build /app/config-server/target/config-server-*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
