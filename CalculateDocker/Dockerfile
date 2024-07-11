FROM openjdk:17-oracle
#VOLUME /data
EXPOSE 6001
ARG JAR_FILE=target/StockCalculator-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]