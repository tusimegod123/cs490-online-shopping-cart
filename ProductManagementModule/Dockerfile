FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} product-module.jar
ENTRYPOINT ["java","-jar","/product-module.jar"]
EXPOSE 8083