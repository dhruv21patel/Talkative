
FROM postgres:16.3-alpine3.20

# Copy create database and user script
COPY ./init-db.sh /docker-entrypoint-initdb.d/

# Give execute permission to besh script
RUN sh -c "chmod +x /docker-entrypoint-initdb.d/init-db.sh"