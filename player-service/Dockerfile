FROM openjdk:17-jdk-slim-buster
ENV PORT 8081
EXPOSE 8081
COPY build/libs/*.jar /app.jar
COPY src/main/resources/application.yml /app/application.yml
ENTRYPOINT ["java", "-jar", "app.jar"]