FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/ui-server-*.war app.war
EXPOSE 9000
ENTRYPOINT ["java","-jar","/app/app.war"]
