FROM openjdk:17-oracle
EXPOSE 6000
ARG JAR_FILE=target/Container1-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
