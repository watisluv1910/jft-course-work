FROM alpine AS build

ARG DB_SCHEMA=jft_database

COPY schema.sql .

RUN apk update && apk add bash

RUN sed "s/\$DB_SCHEMA/$DB_SCHEMA/g" schema.sql > schema_modified.sql

FROM mysql:latest

RUN chown -R mysql:root /var/lib/mysql/

COPY --from=build schema_modified.sql /docker-entrypoint-initdb.d/schema.sql
