#FROM openjdk:17-jdk-slim
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} shoppingcart-module.jar
ENTRYPOINT ["java","-jar","/shoppingcart-module.jar"]
EXPOSE 8084