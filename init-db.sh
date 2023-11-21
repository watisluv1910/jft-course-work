#!/bin/bash

set -e

tr -d '\r' < .env > .env.new && mv .env.new .env

source .env

TMP_SCRIPT=$(mktemp /tmp/temp_schema.XXXXXX)
trap 'rm -f $TMP_SCRIPT' EXIT

while IFS= read -r line; do
  echo "${line//\${DB_SCHEMA}/$DB_SCHEMA}" >> "$TMP_SCRIPT"
done < mysql-database/schema.sql

mysql -u "$DB_USER" -p "$DB_PASSWORD" < "$TMP_SCRIPT"
