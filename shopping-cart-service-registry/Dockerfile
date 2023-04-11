FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} shopping-cart-service-registry.jar
ENTRYPOINT ["java","-jar","/shopping-cart-service-registry.jar"]
EXPOSE 8761