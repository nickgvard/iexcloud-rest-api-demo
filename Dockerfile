FROM openjdk:17

RUN mkdir -p /usr/src/app/
WORKDIR /usr/src/app/

ARG JAR=build/libs/iexcloud-rest-api-demo-0.0.1.jar

ADD ${JAR} iexloud-rest-api.jar

CMD ["java", "-jar", "iexloud-rest-api.jar"]