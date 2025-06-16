# Asterisk Multi-Service Application

This project sets up a Dockerized environment for an Asterisk PBX, a Call Transcriber service, and a Textcom (front-end) service, along with a MySQL database.

## Prerequisites

To run this project, you will need:

1.  **Linux Machine**: This project utilizes `network_mode: host` in its `docker-compose.yml`, which is primarily supported and recommended on Linux hosts. Ensure you are running a Linux distribution.
2.  **Docker and Docker Compose**: Install Docker Engine and Docker Compose on your Linux machine. Follow the official Docker documentation for installation instructions.
3.  **Docker Group Membership**: Your user account must be part of the `docker` group to interact with the Docker daemon without `sudo`. After adding your user to the `docker` group, you **must log out and log back in** (or reboot your machine) for the changes to take effect.
    ```bash
    sudo usermod -aG docker ${USER}
    ```
## Setup Instructions

Follow these steps to get the application running:

1.  **Navigate to the project directory**:
    ```bash
    cd Asterisk-Server-Setup
    ```

2.  **Configure `sip.conf`**:
    Edit the `Asterisk-Server-Setup/config/asterisk/sip.conf` file and update the `externip` variable with your Linux machine's IP address. This is crucial for Asterisk to correctly handle SIP traffic.

    ```ini
    ; Example:
    externip=YOUR_MACHINE_IP_ADDRESS
    ```

3.  **Set Permissions for Recordings Directory**:
    The `asterisk` container needs write permissions to the `recordings` directory to save call recordings. Grant write permissions to others on the `./recordings` directory on your host machine:
    ```bash
    chmod o+w recordings
    ```

4.  **Build and Start Services**:
    The `CallTranscriber` and `Textcom` projects are built as part of their Docker image creation process. From within the `Asterisk-Server-Setup` directory, build the Docker images and start all services in detached mode:
    ```bash
    docker-compose up --build -d
    ```
    *   **Note on MySQL Initialization**: make sure you have no mysql service running in background.

## Accessing Services

*   **Asterisk**: Runs on various UDP ports (5060, 10000-20000 by default).
*   **Textcom (Frontend)**: Accessible via your machine's IP address on port 8080 (e.g., `http://YOUR_MACHINE_IP_ADDRESS:8080`).
*   **CallTranscriber**: This service processes call recordings. It will automatically read files from `/var/spool/asterisk/monitor/` (which is mapped to your host's `./recordings` directory) and update the database.
c
## Troubleshooting

*   **Permission Denied Errors (Docker)**: If you continue to see `Permission denied` errors when running `docker-compose` commands, double-check that you have logged out and logged back in after adding your user to the `docker` group.
*   **`No suitable driver found for jdbc:mysql` (CallTranscriber)**: Ensure the `maven-shade-plugin` is correctly configured in `CallTranscriber/pom.xml` and that the `CallTranscriber/Dockerfile` copies the correct shaded JAR (`CallTranscriber-1.0-SNAPSHOT.jar`). Rebuild the `calltranscriber` service after making changes.
*   **`Invalid signature file digest for Manifest main attributes` (CallTranscriber)**: This is resolved by excluding signature files in the `maven-shade-plugin` configuration within `CallTranscriber/pom.xml`.

If you face further issues, please consult the logs of individual containers using `docker-compose logs <service_name>` (e.g., `docker-compose logs asterisk`). 
If you face further issues, please consult the logs of individual containers using `docker-compose logs <service_name>` (e.g., `docker-compose logs asterisk`). 