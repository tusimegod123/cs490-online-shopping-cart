FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} report-module.jar
ENTRYPOINT ["java","-jar","/report-module.jar"]
EXPOSE 8089