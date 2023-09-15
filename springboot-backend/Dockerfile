FROM maven:3.9.4-amazoncorretto-20-debian as build

WORKDIR /app-server

# Copy the pom.xml file
COPY pom.xml .
COPY src src

RUN mvn clean package

FROM amazoncorretto:20-alpine-jdk

RUN apk update && apk add bash dos2unix

COPY wait-for-it.sh /wait-for-it.sh

RUN \
    dos2unix /wait-for-it.sh &&  \
    chmod +x /wait-for-it.sh

COPY --from=build /app-server/target/*.jar app.jar

CMD ["/bin/sh", "-c", "/wait-for-it.sh app-db:${DB_DEFAULT_PORT} -s -- java -jar app.jar"]
