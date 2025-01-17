
#!/bin/bash
set -e  # Exit immediately if any command fails

# Load environment variables
DB_NAME=${DB_NAME:-ChatTable}
DB_USER=${DB_USER:-defaultuser}
DB_PASSWORD=${DB_PASSWORD:-defaultpassword}

# Connect to the default 'postgres' database to create the new database
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
    CREATE DATABASE $DB_NAME;
EOSQL

# Connect to the newly created database and create the user
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$DB_NAME" <<-EOSQL
    -- Create the user if it does not already exist
    DO \$\$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = '$DB_USER') THEN
            CREATE USER $DB_USER WITH PASSWORD '$DB_PASSWORD';
        END IF;
    END
    \$\$;

    -- Grant all privileges to the user on the database
    GRANT ALL PRIVILEGES ON DATABASE $DB_NAME TO $DB_USER;

    -- Make the user a superuser
    ALTER USER $DB_USER WITH SUPERUSER;
EOSQL
