FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/payment-service-*.jar app.jar
EXPOSE 9005
ENTRYPOINT ["java","-jar","/app/app.jar"]
