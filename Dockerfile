# Этап 1 - сборка проекта в jar
FROM maven:3.8.5-openjdk-17 as maven

WORKDIR /job4j_tracker

COPY . /job4j_tracker

RUN mvn package -Dmaven.test.skip=true

# Этап 2 - указание как запустить проект
FROM openjdk:17.0.2-jdk

WORKDIR /job4j_tracker

COPY --from=maven /job4j_tracker/target/tracker.jar tracker.jar

CMD java -jar tracker.jar