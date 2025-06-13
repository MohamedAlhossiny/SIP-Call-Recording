#!/bin/bash

# Function to check if Docker is running
check_docker() {
    if ! docker info >/dev/null 2>&1; then
        echo "Error: Docker is not running or you don't have permissions"
        exit 1
    fi
}

# Function to create required directories
create_directories() {
    mkdir -p config/asterisk recordings logs mysql-data
    chmod -R 755 recordings logs
}

# Function to start services
start_services() {
    echo "Starting Asterisk services..."
    docker-compose up -d
    echo "Waiting for services to be ready..."
    sleep 5
    docker-compose ps
}

# Function to stop services
stop_services() {
    echo "Stopping Asterisk services..."
    docker-compose down
}

# Function to show logs
show_logs() {
    if [ "$1" ]; then
        docker-compose logs -f "$1"
    else
        docker-compose logs -f
    fi
}

# Function to show status
show_status() {
    echo "Container Status:"
    docker-compose ps
    echo -e "\nNetwork Status:"
    docker network ls | grep asterisk
    echo -e "\nAsterisk SIP Peers:"
    docker-compose exec asterisk asterisk -rx "sip show peers"
}

# Function to restart services
restart_services() {
    stop_services
    start_services
}

# Main script
case "$1" in
    start)
        check_docker
        create_directories
        start_services
        ;;
    stop)
        check_docker
        stop_services
        ;;
    restart)
        check_docker
        restart_services
        ;;
    status)
        check_docker
        show_status
        ;;
    logs)
        check_docker
        show_logs "$2"
        ;;
    cli)
        check_docker
        docker-compose exec asterisk asterisk -rvvv
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status|logs|cli}"
        echo "Options:"
        echo "  start   - Start Asterisk services"
        echo "  stop    - Stop Asterisk services"
        echo "  restart - Restart Asterisk services"
        echo "  status  - Show services status"
        echo "  logs    - Show logs (optionally specify service name)"
        echo "  cli     - Access Asterisk CLI"
        exit 1
        ;;
esac

exit 0 