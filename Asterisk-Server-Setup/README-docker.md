# Dockerized Asterisk 18 Server with Real-Time MySQL

This repository contains a Docker Compose setup for running Asterisk 18 with real-time MySQL architecture.

## Prerequisites

- Docker
- Docker Compose

## Directory Structure

```
.
├── config/
│   ├── asterisk/
│   │   ├── extconfig.conf
│   │   ├── func_odbc.conf
│   │   └── res_odbc.conf
│   ├── odbc.ini
│   └── odbcinst.ini
├── recordings/
├── mysql-data/
├── Database.sql
├── docker-compose.yml
├── Dockerfile
└── docker-entrypoint.sh
```

## Quick Start

1. Clone this repository:
```bash
git clone <repository-url>
cd <repository-directory>
```

2. Create necessary directories:
```bash
mkdir -p config/asterisk recordings mysql-data
```

3. Start the services:
```bash
docker-compose up -d
```

4. Monitor the logs:
```bash
docker-compose logs -f
```

## Default Configuration

- MySQL Database:
  - Database: asterisk
  - User: asterisk
  - Password: 123
  - Root Password: rootpassword

- Sample SIP User:
  - Extension: 1005
  - Password: 1234

## Ports

- SIP: 5060/UDP
- RTP: 10000-20000/UDP
- MySQL: 3306/TCP

## Adding New SIP Users

You can add new SIP users by connecting to the MySQL database:

```bash
docker-compose exec mysql mysql -u asterisk -p123 asterisk
```

Then insert a new user:

```sql
INSERT INTO sipfriends (
    name,
    defaultuser,
    host,
    type,
    context,
    md5secret,
    dtmfmode,
    nat,
    disallow,
    allow,
    dynamic
) VALUES (
    '1006',
    '1006',
    'dynamic',
    'friend',
    'default',
    'md5-hash-of-password',
    'rfc2833',
    'force_rport,comedia',
    NULL,
    'ulaw,alaw',
    'yes'
);
```

## Accessing Asterisk CLI

To access the Asterisk CLI:

```bash
docker-compose exec asterisk asterisk -rvvv
```

## Call Recordings

Call recordings are stored in the `recordings` directory on your host machine.

## Troubleshooting

1. Check service status:
```bash
docker-compose ps
```

2. View logs:
```bash
docker-compose logs -f asterisk  # Asterisk logs
docker-compose logs -f mysql     # MySQL logs
```

3. Check ODBC connection:
```bash
docker-compose exec asterisk isql -v asterisk
```

4. Verify SIP peers:
```bash
docker-compose exec asterisk asterisk -rx "sip show peers"
```

## Security Notes

1. Change default passwords in:
   - docker-compose.yml (MySQL passwords)
   - config/odbc.ini
   - config/asterisk/res_odbc.conf

2. Consider using Docker secrets for sensitive information in production

3. Restrict access to the MySQL port (3306) in production environments 