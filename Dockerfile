FROM openjdk:17
WORKDIR /prepare
COPY src/main Manifest.txt ./
COPY data ./data
RUN javac -d . Solution.java && jar cfm Solution.jar Manifest.txt main/*.class

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=0 /prepare/ ./
ENTRYPOINT ["java", "-jar", "Solution.jar"]
