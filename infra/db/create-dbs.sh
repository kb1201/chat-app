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

# Function to create and fill chat tables
function create_and_fill_chat_tables() {
  echo "Creating tables in chat"
  psql -v ON_ERROR_STOP=1 --username "chat"  <<-EOSQL



    CREATE TABLE IF NOT EXISTS rooms
    (
        id uuid PRIMARY KEY,
        name TEXT NOT NULL,
        owner_id uuid REFERENCES users(id)
    );

    CREATE TABLE IF NOT EXISTS room_members
    (
        room_id uuid REFERENCES rooms(id),
        user_id uuid NOT NULL,  -- The user who is a member of the room
        PRIMARY KEY (room_id, user_id)
    );

    CREATE TABLE IF NOT EXISTS messages
    (
        id uuid PRIMARY KEY,
        content TEXT NOT NULL,
        created TIMESTAMP NOT NULL DEFAULT NOW(),
        user_id uuid NOT NULL,
        room_id uuid REFERENCES rooms(id)
    );

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
