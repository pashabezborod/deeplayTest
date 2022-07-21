FROM maven:3.8.3-openjdk-16-slim
WORKDIR /prepare
COPY . .
RUN mvn package

FROM openjdk:16-alpine
WORKDIR /app
COPY --from=0 /prepare/ .
ENTRYPOINT ["java", "-jar", "target/deeplayTest-1.0.jar"]
