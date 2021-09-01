FROM openjdk:11-jre

ARG JAR_FILE

ADD target/${JAR_FILE} /app.jar

ENTRYPOINT ["java","-Dserver.port=8080","-jar","/app.jar"]
