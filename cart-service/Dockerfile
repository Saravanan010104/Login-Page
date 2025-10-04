FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/cart-service-*.jar app.jar
EXPOSE 9003
ENTRYPOINT ["java","-jar","/app/app.jar"]
