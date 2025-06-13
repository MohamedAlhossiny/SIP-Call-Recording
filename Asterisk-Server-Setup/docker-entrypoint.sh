#!/bin/bash
set -e

# Maximum number of retries
MAX_RETRIES=10
# Delay between retries (in seconds)
RETRY_DELAY=2

echo "Waiting for MySQL to be ready..."
for i in $(seq 1 $MAX_RETRIES); do
    if nc -z mysql 3306; then
        echo "MySQL is ready!"
        break
    fi
    
    if [ $i -eq $MAX_RETRIES ]; then
        echo "Error: MySQL did not become ready in time"
        exit 1
    fi
    
    echo "Attempt $i/$MAX_RETRIES: MySQL is not ready yet..."
    sleep $RETRY_DELAY
done

# Start Asterisk
echo "Starting Asterisk..."
exec "$@" 