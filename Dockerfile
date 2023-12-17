FROM openjdk:20
LABEL authors="Dionata Silva"

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]