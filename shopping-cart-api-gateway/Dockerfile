FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} shopping-cart-api-gateway.jar
ENTRYPOINT ["java","-jar","/shopping-cart-api-gateway.jar"]
EXPOSE 8080