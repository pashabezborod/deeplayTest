FROM openjdk:17
WORKDIR /app
COPY ./src/main .
RUN javac -d . ./*
ENTRYPOINT ["java", "-classpath", "./", "main.Solution"]