#!/bin/bash

set -e

# Set the current directory to the script's directory
cd "$(dirname "${BASH_SOURCE[0]}")"

tr -d '\r' < ../.env.example > .env.tmp && mv .env.tmp ../.env

source ../.env

TMP_SCRIPT=$(mktemp /tmp/temp_schema.XXXXXX)
trap 'rm -f $TMP_SCRIPT' EXIT

while IFS= read -r line; do
  echo "${line//\$DB_SCHEMA/$MYSQL_DATABASE}" >> "$TMP_SCRIPT"
done < schema.sql

# Using --defaults-extra-file with printf to prevent
# 'Using a password on the command line interface can be insecure' warning
mysql --defaults-extra-file=<(printf "[client]\nuser = %s\npassword = %s" "$MYSQL_USER" "$MYSQL_PASSWORD") < "$TMP_SCRIPT"
