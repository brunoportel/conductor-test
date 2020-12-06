FROM openjdk:11.0.9.1-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN ls -l
ENTRYPOINT ["java","-jar","app.jar"]