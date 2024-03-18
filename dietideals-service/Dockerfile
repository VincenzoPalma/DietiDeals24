FROM openjdk:17

WORKDIR /app

COPY ./build/libs/dietideals-service-0.0.1-SNAPSHOT.jar /app/dietideals-service-0.0.1-SNAPSHOT.jar

COPY src/main/resources/application.yml /app/application.yml

EXPOSE 8080

CMD ["java", "-jar", "/app/dietideals-service-0.0.1-SNAPSHOT.jar"]