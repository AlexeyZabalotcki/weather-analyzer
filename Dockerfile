FROM openjdk:17
WORKDIR /app
COPY build/libs/weather-analyzer-0.0.1-SNAPSHOT.jar weather-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "weather-0.0.1-SNAPSHOT.jar"]
