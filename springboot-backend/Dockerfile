FROM maven:3-amazoncorretto-21-alpine as build

WORKDIR /app-server

# Copy the pom.xml file
COPY pom.xml .
COPY src src

RUN mvn clean package

FROM amazoncorretto:21-alpine-jdk

RUN apk update && apk add bash dos2unix

COPY ../../server_side_dev/pract_spring/wait-for-it.sh /wait-for-it.sh

RUN \
    dos2unix /wait-for-it.sh &&  \
    chmod +x /wait-for-it.sh

COPY --from=build /app-server/target/*.jar app.jar

CMD ["/bin/sh", "-c", "/wait-for-it.sh ${DB_SERVER}:${DB_DEFAULT_PORT} -s -- java -jar app.jar"]
