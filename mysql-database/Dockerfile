FROM alpine AS build

ARG MYSQL_DATABASE=jft_database

WORKDIR /tmp

COPY schema.sql .

RUN apk update && apk add bash

RUN sed "s/\${DB_SCHEMA}/$MYSQL_DATABASE/g" schema.sql > /tmp/schema_modified.sql

FROM mysql:latest

RUN chown -R mysql:root /var/lib/mysql/

COPY --from=build /tmp/schema_modified.sql /docker-entrypoint-initdb.d/schema.sql
