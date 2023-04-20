#FROM maven:3.8.3-openjdk-17
#WORKDIR /app
#COPY . .
#RUN mvn clean install
#CMD mvn spring-boot:run

FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} profit-module.jar
ENTRYPOINT ["java","-jar","/profit-module.jar"]
EXPOSE 8087
