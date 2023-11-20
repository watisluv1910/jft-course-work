FROM mysql:latest

RUN chown -R mysql:root /var/lib/mysql/

ADD schema.sql /etc/mysql/schema.sql

RUN cp /etc/mysql/schema.sql /docker-entrypoint-initdb.d