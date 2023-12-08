#!/bin/bash

set -e

# Set the current directory to the script's directory
cd "$(dirname "${BASH_SOURCE[0]}")"

tr -d '\r' < .env > .env.new && mv .env.new .env

source ../.env

TMP_SCRIPT=$(mktemp /tmp/temp_schema.XXXXXX)
trap 'rm -f $TMP_SCRIPT' EXIT

while IFS= read -r line; do
  echo "${line//\${DB_SCHEMA}/$MYSQL_DATABASE}" >> "$TMP_SCRIPT"
done < schema.sql

mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" < "$TMP_SCRIPT"
