FROM maven:3-eclipse-temurin-17-alpine as build
WORKDIR /app-server

COPY pom.xml .
COPY .env.example ./backend/.env

COPY backend/pom.xml ./backend/pom.xml
COPY backend/src ./backend/src

# Build the backend module
RUN mvn -f backend/pom.xml clean package -Dmaven.test.skip=true

RUN apk update && apk add bash dos2unix

COPY backend/wait-for-it.sh wait-for-it.sh

RUN \
    dos2unix wait-for-it.sh &&  \
    chmod +x wait-for-it.sh

FROM maven:3-eclipse-temurin-17-alpine

COPY --from=build /app-server/backend/target/*.jar app.jar
COPY --from=build /app-server/wait-for-it.sh wait-for-it.sh

CMD ["/bin/sh", "-c", "/wait-for-it.sh ${DB_SERVER}:${MYSQL_TCP_PORT} -s -- java -jar app.jar"]
