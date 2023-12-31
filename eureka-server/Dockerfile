FROM openjdk:17-jdk-slim-buster
ENV PORT 8761
EXPOSE 8761
COPY build/libs/*.jar /app.jar
COPY src/main/resources/application.yml /app/application.yml
ENTRYPOINT ["java", "-jar", "app.jar"]