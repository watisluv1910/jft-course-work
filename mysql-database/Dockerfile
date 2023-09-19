FROM mysql:latest

RUN chown -R mysql:root /var/lib/mysql/

ADD create_jft_database.sql /etc/mysql/create_jft_database.sql

RUN cp /etc/mysql/create_jft_database.sql /docker-entrypoint-initdb.d