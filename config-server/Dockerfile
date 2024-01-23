FROM openjdk:17-jdk-slim-buster
ENV PORT 8888
EXPOSE 8888
COPY build/libs/*.jar /app.jar
COPY src/main/resources/application-docker.yml /app/application.yml
COPY src/main/resources/config/api-gateway.yml /app/config/api-gateway.yml
COPY src/main/resources/config/music-service.yml /app/config/music-service.yml
COPY src/main/resources/config/player-service.yml /app/config/player-service.yml
COPY src/main/resources/config/user-service.yml /app/config/user-service.yml
ENTRYPOINT ["java", "-jar", "app.jar"]