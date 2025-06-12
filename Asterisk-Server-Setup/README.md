# Asterisk 18 Server Setup with Real-Time MySQL Architecture

This guide provides step-by-step instructions for setting up Asterisk 18 server on Debian with real-time architecture using MySQL/MariaDB for user management through ODBC.

## Installation Steps

### 1. System Update
```bash
sudo apt update && sudo apt upgrade
```

### 2. Install Required Dependencies
```bash
sudo apt install wget build-essential subversion libmysqlclient-dev
```
Note: Additional MySQL dependencies might be required depending on your system configuration.

### 3. Download and Extract Asterisk
```bash
cd /usr/src/
sudo wget http://downloads.asterisk.org/pub/telephony/asterisk/asterisk-18-current.tar.gz
sudo tar zxf asterisk-18-current.tar.gz
cd asterisk-18.*/
```

### 4. Install Prerequisites and MP3 Support
```bash
sudo contrib/scripts/get_mp3_source.sh
sudo contrib/scripts/install_prereq install
```

### 5. Configure Asterisk
```bash
sudo ./configure
```

### 6. Enable Required Modules
```bash
make menuselect --enable res_config_mysql \
                --enable app_mysql \
                --enable format_mp3 \
                menuselect.makeopts
```

### 7. Compile and Install
```bash
sudo make -j12
sudo make install
sudo make samples  # Note: This will override existing configuration files
sudo make config
sudo ldconfig
```

### 8. Start Asterisk Service
```bash
sudo systemctl start asterisk
```

### 9. Access Asterisk CLI
```bash
sudo asterisk -vvvr
```

### 10. Configure Firewall
```bash
sudo ufw allow 5060/udp
```

## Database Setup

### 1. Install MySQL Server and ODBC Dependencies
```bash
sudo apt install mysql-server unixodbc unixodbc-dev odbc-mariadb
```

### 2. Create Asterisk Database and User
```bash
sudo mysql -u root -p
```

```sql
CREATE DATABASE asterisk;
CREATE USER 'asterisk'@'localhost' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON asterisk.* TO 'asterisk'@'localhost';
FLUSH PRIVILEGES;
USE asterisk;
```

### 3. Import Database Schema
Save the provided `Database.sql` file and import it:
```bash
mysql -u asterisk -p asterisk < Database.sql
```

## Configuration Files Setup

The Asterisk configuration files should be placed in `/etc/asterisk/`, while the ODBC configuration files need to be placed in `/etc/`.

### 1. ODBC Configuration

#### a. /etc/odbcinst.ini
```ini
[MariaDB]
Driver=/usr/lib/x86_64-linux-gnu/odbc/libmaodbc.so
Description=MariaDB Connector/ODBC(Unicode)
Threading=0
UsageCount=1
```

#### b. /etc/odbc.ini
```ini
[asterisk]
Description = MySQL Asterisk Realtime
Driver = MariaDB
Server = 127.0.0.1
Database = asterisk
User = asterisk
Password = 123
Port = 3306
```

#### c. /etc/asterisk/res_odbc.conf
```ini
[asterisk]
enabled => yes
dsn => asterisk
username => asterisk
password => 123
pre-connect => yes
```

### 2. Asterisk Configuration Files

#### a. extconfig.conf
Configure real-time database:
```ini
[settings]
ps_endpoints => odbc,asterisk,ps_endpoints ; PJSIP
ps_auths => odbc,asterisk,ps_auths ; PJSIP
ps_aors => odbc,asterisk,ps_aors ; PJSIP
sippeers => odbc,asterisk,sipfriends
sipusers => odbc,asterisk,sipfriends
sipfriends => odbc,asterisk,sipfriends
```

#### b. sip.conf
Basic SIP configuration:
```ini
[general]
context=default
bindport=5060
bindaddr=0.0.0.0
allowguest=no
disallow=all
allow=ulaw
allow=alaw

rtcachefriends=yes
rtsaveonregister=yes
rtupdate=yes
ignoreregexpire=yes


```

#### c. extensions.conf
This configuration handles the dialplan and call recording:
```ini
[general]
autofallthrough=yes

[globals]
RECORDING_STORAGE_DIR=/var/spool/asterisk/monitor/

[default]
; Handle 4-digit extensions
exten => _XXXX,1,NoOp(Call to ${EXTEN} from ${CALLERID(num)}, UniqueID: ${UNIQUEID})
    same => n,Answer()
    same => n,Set(EPOCH_START=${EPOCH})
    same => n,Set(CALLER=${CALLERID(num)})
    same => n,Set(CALLEE=${EXTEN})
    same => n,Set(DATESTAMP=${STRFTIME(${EPOCH},,%Y%m%d)})
    same => n,Set(CALLFILENAME=${RECORDING_STORAGE_DIR}${DATESTAMP}-${CALLER}-${CALLEE}-${UNIQUEID})
    same => n,MixMonitor(${CALLFILENAME}.wav)
    same => n,Dial(SIP/${EXTEN},20,Ttr)
    same => n,Hangup()

; Hangup handler to log call details using ODBC function
exten => h,1,NoOp(Hangup handler for ${CHANNEL})
    same => n,Set(EPOCH_END=${EPOCH})
    same => n,Set(ODBC_INSERT_CALLLOG(${EPOCH_START},${EPOCH_END},${CALLER},${CALLEE},${CALLFILENAME}.wav)=1)
```

#### d. func_odbc.conf
Configure ODBC functions for database operations:
```ini
[general]
; General settings for ODBC functions

[INSERT_CALLLOG]
dsn=asterisk
writesql=INSERT INTO calllogs (
    StartTimestamp, 
    EndTimestamp, 
    CallingParty, 
    CalledParty, 
    Path_WAV_File, 
    IsCallTransferred
) VALUES (
    FROM_UNIXTIME(${ARG1}), 
    FROM_UNIXTIME(${ARG2}), 
    '${ARG3}', 
    '${ARG4}', 
    '${ARG5}', 
    FALSE
)
```

The `func_odbc.conf` configuration:
1. Defines custom ODBC functions that can be used in the dialplan
2. `INSERT_CALLLOG` function:
   - Takes 5 arguments: start time, end time, caller, callee, and recording path
   - Inserts call details into the `calllogs` table
   - Timestamps are converted from UNIX epoch using `FROM_UNIXTIME`

## System Setup

### 1. Directory and Permissions Setup
```bash
# Create and set permissions for recording directory
sudo mkdir -p /var/spool/asterisk/monitor
sudo chown -R asterisk:asterisk /var/spool/asterisk/monitor
sudo chmod -R 755 /var/spool/asterisk/monitor

# Set Asterisk configuration permissions
sudo chown -R asterisk:asterisk /etc/asterisk/
sudo chmod -R 750 /etc/asterisk/
```

### 2. Database Table for Call Logs
```sql
CREATE TABLE calllogs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    StartTimestamp DATETIME,
    EndTimestamp DATETIME,
    CallingParty VARCHAR(20),
    CalledParty VARCHAR(20),
    Path_WAV_File VARCHAR(255),
    IsCallTransferred BOOLEAN
);
```



## Testing the Configuration

1. Restart Asterisk:
```bash
sudo service asterisk restart
```

1. Test ODBC Connection:
```bash
isql -v asterisk-connector
```

1. Test in Asterisk CLI:
```bash
asterisk -rx "odbc show"
asterisk -rx "sip show peers"
```

> **Important Note**: If `odbc show` indicates a successful connection but `sip show peers` doesn't show any users, this is normal! SIP peers will only become visible in the `sip show peers` output after they have successfully registered using a SIP client (such as Linephone, Zoiper, etc.). This is because the realtime configuration loads user information dynamically upon registration.

To verify a working setup:
1. First confirm ODBC is connected using `odbc show`
2. Add a user to the database
3. Configure a SIP client with the user credentials
4. After the client registers successfully, the user will appear in `sip show peers`

You can also check registration attempts and troubleshoot connection issues by watching the Asterisk logs:
```bash
tail -f /var/log/asterisk/messages
```

## Adding SIP Users

You can add SIP users directly to the database. Here's an example:

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
    allow,
    dynamic
) VALUES (
    '1005',
    '1005',
    'dynamic',
    'friend',
    'default',
    'caf1a3dfb505ffed0d024130f58c5cfa',  -- MD5 hash of '1005:asterisk:1234'
    'rfc2833',
    'force_rport,comedia',
    'ulaw,alaw',
    'yes'
);
```

### Important Notes About User Configuration

1. **Computing MD5 Secret**
   To generate the MD5 secret in the correct format, use this command in Asterisk CLI:
   ```bash
   asterisk -rx "module load res_crypto && echo -n '1005:asterisk:1234' | md5sum"
   ```
   The format is `username:realm:password`. In the example above:
   - username: 1005
   - realm: asterisk
   - password: 1234

2. **Codec Configuration (CRITICAL)**
   - Always set the `disallow` column to NULL
   - Setting `disallow='all'` will clear ALL supported codecs for that user
   - Recommended configuration:
     ```sql
     disallow=NULL    -- Let the system use the default codec settings
     allow='ulaw,alaw'  -- Specify additional allowed codecs
     ```

3. **Example with Complete Codec Configuration**
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
       '1005',
       '1005',
       'dynamic',
       'friend',
       'default',
       'caf1a3dfb505ffed0d024130f58c5cfa',
       'rfc2833',
       'force_rport,comedia',
       NULL,           -- Use system default codec settings
       'ulaw,alaw',    -- Add specific codecs
       'yes'
   );
   ```

## Important Notes

1. The `make samples` command will override all existing configuration files. Use with caution on existing installations.
2. Real-time architecture requires proper MySQL/MariaDB setup and ODBC configuration.
3. Ensure all necessary ports are opened in your firewall according to your needs.

## Troubleshooting

If you encounter any issues:
1. Check Asterisk logs: `sudo tail -f /var/log/asterisk/full`
2. Verify MySQL connectivity
3. Ensure all required modules are loaded: `module show` in Asterisk CLI

### Common Issues

1. ODBC Connection Problems:
   - Check ODBC logs: `tail -f /var/log/odbc.log`
   - Verify MySQL service is running: `systemctl status mysql`
   - Test database connection: `mysql -u asteriskuser -p asterisk`

2. SIP Registration Issues:
   - Check Asterisk logs: `tail -f /var/log/asterisk/messages`
   - Verify network connectivity: `netstat -plunt | grep asterisk`
   - Check SIP peer status: `asterisk -rx "sip show peers"`

## Security Considerations

1. Change default passwords
2. Configure proper firewall rules
3. Use secure protocols where possible
4. Regularly update your system and Asterisk installation
