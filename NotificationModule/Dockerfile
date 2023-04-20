FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} notification-module.jar
ENTRYPOINT ["java","-jar","/notification-module.jar"]
EXPOSE 8088