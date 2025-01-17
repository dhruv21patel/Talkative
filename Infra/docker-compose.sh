#!/bin/bash

# Define paths to Docker Compose files
AUTH_COMPOSE_FILE="docker-compose-Auth.yml"
CHAT_COMPOSE_FILE="docker-compose-Chat.yml"
KAFKA_COMPOSE_FILE="docker-compose-Kafka.yml"

# Function to display usage
usage() {
    echo "Usage: $0 {start|stop|status|restart|logs}"
    echo "  start    - Start all services"
    echo "  stop     - Stop all services"
    echo "  status   - Show the status of all services"
    echo "  restart  - Restart all services"
    echo "  logs     - Show logs for all services"
    exit 1
}

# Check if an argument is provided
if [ $# -eq 0 ]; then
    usage
fi

# Parse the command
COMMAND=$1

case "$COMMAND" in
    start)
        echo "Starting Auth services..."
        docker-compose -f $AUTH_COMPOSE_FILE up -d
        echo "Starting Chat services..."
        docker-compose -f $CHAT_COMPOSE_FILE up -d
        echo "Starting Kafka services..."
        docker-compose -f $KAFKA_COMPOSE_FILE up -d
        ;;
    stop)
        echo "Stopping all services..."
        docker-compose -f $AUTH_COMPOSE_FILE down
        docker-compose -f $CHAT_COMPOSE_FILE down
        docker-compose -f $KAFKA_COMPOSE_FILE down
        ;;
    status)
        echo "Status of AUTH services:"
        docker-compose -f $AUTH_COMPOSE_FILE ps
        echo
        echo "Status of Chat services:"
        docker-compose -f $CHAT_COMPOSE_FILE ps
        echo
        echo "Status of KAFKA services:"
        docker-compose -f $KAFKA_COMPOSE_FILE ps
        ;;
    restart)
        echo "Restarting all services..."
        docker-compose -f $AUTH_COMPOSE_FILE down
        docker-compose -f $CHAT_COMPOSE_FILE down
        docker-compose -f $KAFKA_COMPOSE_FILE down
        docker-compose -f $AUTH_COMPOSE_FILE up -d
        docker-compose -f $CHAT_COMPOSE_FILE up -d
        docker-compose -f $KAFKA_COMPOSE_FILE up -d
        ;;
    logs)
        echo "Showing logs for AUTH services..."
        docker-compose -f $AUTH_COMPOSE_FILE logs
        echo
        echo "Showing logs for Chat services..."
        docker-compose -f $CHAT_COMPOSE_FILE logs
        echo
        echo "Showing logs for KAFKA services..."
        docker-compose -f $KAFKA_COMPOSE_FILE logs
        ;;
    *)
        echo "Invalid command!"
        usage
        ;;
esac
