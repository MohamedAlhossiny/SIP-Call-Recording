
-- Create and use the database
CREATE DATABASE IF NOT EXISTS asterisk;
USE asterisk;

-- Drop tables if they exist
DROP TABLE IF EXISTS transactionlogs;
DROP TABLE IF EXISTS calllogs;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS sipfriends;

-- Unified accounts table for both users and admins
CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role ENUM('user', 'admin') NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    passwd TEXT NOT NULL,
    email VARCHAR(100) UNIQUE,
    full_name VARCHAR(100),
    misdsn VARCHAR(20) UNIQUE -- Only for users; can be NULL for admins
);

-- Call logs table
CREATE TABLE calllogs (
    CallID INT AUTO_INCREMENT PRIMARY KEY,
    StartTimestamp DATETIME,
    EndTimestamp DATETIME,
    CallingParty VARCHAR(50),
    CalledParty VARCHAR(50),
    Path_WAV_File VARCHAR(255),
    IsCallTransferred BOOLEAN
);

-- Transaction logs table
CREATE TABLE transactionlogs (
    TransactionID INT AUTO_INCREMENT PRIMARY KEY,
    CallID INT,
    Text TEXT,
    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CallID) REFERENCES calllogs(CallID) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS sipfriends (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) UNIQUE NOT NULL,
    ipaddr VARCHAR(40),
    port INT,
    regseconds INT,
    defaultuser VARCHAR(40),
    fullcontact VARCHAR(80),
    regserver VARCHAR(20),
    useragent VARCHAR(255),
    lastms INT,
    host VARCHAR(40),
    type ENUM('friend','user','peer'),
    context VARCHAR(40),
    permit VARCHAR(95),
    deny VARCHAR(95),
    secret VARCHAR(40),
    md5secret VARCHAR(40),
    remotesecret VARCHAR(40),
    transport VARCHAR(40),
    dtmfmode VARCHAR(40),
    directmedia VARCHAR(40),
    nat VARCHAR(40),
    callgroup VARCHAR(40),
    pickupgroup VARCHAR(40),
    language VARCHAR(40),
    disallow VARCHAR(40),
    allow VARCHAR(40),
    insecure VARCHAR(40),
    trustrpid ENUM('yes','no'),
    progressinband ENUM('yes','no','never'),
    promiscredir ENUM('yes','no'),
    useclientcode ENUM('yes','no'),
    accountcode VARCHAR(40),
    setvar VARCHAR(40),
    callerid VARCHAR(40),
    amaflags VARCHAR(40),
    callcounter ENUM('yes','no'),
    busylevel INT,
    allowoverlap ENUM('yes','no'),
    allowsubscribe ENUM('yes','no'),
    videosupport ENUM('yes','no'),
    maxcallbitrate INT,
    rfc2833compensate ENUM('yes','no'),
    mailbox VARCHAR(40),
    session_timers ENUM('accept','refuse','originate'),
    session_expires INT,
    session_minse INT,
    session_refresher ENUM('uac','uas'),
    t38pt_usertpsource VARCHAR(40),
    regexten VARCHAR(40),
    fromdomain VARCHAR(40),
    fromuser VARCHAR(40),
    `qualify` VARCHAR(40),
    defaultip VARCHAR(40),
    rtptimeout INT,
    rtpholdtimeout INT,
    sendrpid ENUM('yes','no'),
    outboundproxy VARCHAR(40),
    callbackextension VARCHAR(40),
    timert1 INT,
    timerb INT,
    qualifyfreq INT,
    constantssrc ENUM('yes','no'),
    contactpermit VARCHAR(95),
    contactdeny VARCHAR(95),
    usereqphone ENUM('yes','no'),
    textsupport ENUM('yes','no'),
    faxdetect ENUM('yes','no'),
    buggymwi ENUM('yes','no'),
    auth VARCHAR(40),
    fullname VARCHAR(40),
    trunkname VARCHAR(40),
    cid_number VARCHAR(40),
    callingpres ENUM('allowed_not_screened','allowed_passed_screen','allowed_failed_screen','allowed','prohib_not_screened','prohib_passed_screen','prohib_failed_screen','prohib'),
    mohinterpret VARCHAR(40),
    mohsuggest VARCHAR(40),
    parkinglot VARCHAR(40),
    hasvoicemail ENUM('yes','no'),
    subscribemwi ENUM('yes','no'),
    vmexten VARCHAR(40),
    autoframing ENUM('yes','no'),
    rtpkeepalive INT,
    call_limit INT,
    g726nonstandard ENUM('yes','no'),
    ignoresdpversion ENUM('yes','no'),
    allowtransfer ENUM('yes','no'),
    dynamic ENUM('yes','no'),
    path VARCHAR(256),
    supportpath ENUM('yes','no')
);

