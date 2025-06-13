-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS asterisk;

-- Create user if it doesn't exist and grant privileges
CREATE USER IF NOT EXISTS 'asterisk'@'%' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON asterisk.* TO 'asterisk'@'%';
FLUSH PRIVILEGES;

-- Switch to asterisk database
USE asterisk;

-- Create call logs table
CREATE TABLE IF NOT EXISTS calllogs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    StartTimestamp DATETIME,
    EndTimestamp DATETIME,
    CallingParty VARCHAR(20),
    CalledParty VARCHAR(20),
    Path_WAV_File VARCHAR(255),
    IsCallTransferred BOOLEAN
);

-- Create SIP friends table
CREATE TABLE IF NOT EXISTS sipfriends (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40),
    defaultuser VARCHAR(40),
    host VARCHAR(40),
    type ENUM('friend','user','peer'),
    context VARCHAR(40),
    md5secret VARCHAR(40),
    dtmfmode VARCHAR(10),
    nat VARCHAR(100),
    disallow VARCHAR(100),
    allow VARCHAR(100),
    dynamic ENUM('yes','no')
);

-- Insert a sample SIP user
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
    NULL,
    'ulaw,alaw',
    'yes'
); 