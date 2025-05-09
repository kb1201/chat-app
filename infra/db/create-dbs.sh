#!/bin/bash

set -e
set -u

# Function to create user and database
function create_user_and_database() {
  local database=$1
  echo "  Creating user and database '$database'"
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
      CREATE USER $database WITH PASSWORD 'changeme';
      CREATE DATABASE $database;
      GRANT ALL PRIVILEGES ON DATABASE $database TO $database;
      \c $database;
      CREATE SCHEMA $database;
      GRANT ALL ON SCHEMA $database TO $database;
      GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA $database TO $database;
      GRANT ALL ON ALL SEQUENCES IN SCHEMA $database TO $database;
      GRANT ALL ON ALL FUNCTIONS IN SCHEMA $database TO $database;
EOSQL
}

# Main execution block for multiple database creation
if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
  echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
  for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
    create_user_and_database $db
  done
  echo "Multiple databases created"

fi
