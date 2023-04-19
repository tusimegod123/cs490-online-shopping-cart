FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV DB_URL=jdbc:mysql://localhost:3306/shoppingcartmodule \
    DB_USERNAME=root \
    DB_PASSWORD=Realkhiw@123
ENTRYPOINT ["java","-jar","/app.jar"]
