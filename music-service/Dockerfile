FROM openjdk:17-jdk-slim-buster
ENV PORT 8085
EXPOSE 8085
COPY build/libs/*.jar /app.jar
COPY src/main/resources/application.yml /app/application.yml
ENTRYPOINT ["java", "-jar", "app.jar"]