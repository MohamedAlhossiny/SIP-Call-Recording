FROM debian:bullseye-slim

# Create asterisk user and group first
RUN groupadd -r asterisk && useradd -r -g asterisk asterisk

# Install dependencies
RUN apt-get update && apt-get install -y \
    wget \
    build-essential \
    subversion \
    libmariadb-dev \
    libmariadb-dev-compat \
    unixodbc \
    unixodbc-dev \
    odbc-mariadb \
    netcat \
    libxml2-dev \
    libncurses5-dev \
    uuid-dev \
    sqlite3 \
    libsqlite3-dev \
    pkg-config \
    libjansson-dev \
    libssl-dev \
    libedit-dev \
    git \
    curl \
    # Additional dependencies that would normally be installed by install_prereq
    bzip2 \
    file \
    libasound2-dev \
    libc6-dev \
    libcurl4-openssl-dev \
    libedit-dev \
    libgsm1-dev \
    libogg-dev \
    libpopt-dev \
    libresample1-dev \
    libspandsp-dev \
    libspeex-dev \
    libspeexdsp-dev \
    libsrtp2-dev \
    libssl-dev \
    libvorbis-dev \
    libxml2-dev \
    linux-headers-generic \
    portaudio19-dev \
    python3-dev \
    unixodbc \
    unixodbc-dev \
    uuid \
    uuid-dev \
    xmlstarlet \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /usr/src

# Download and extract Asterisk
RUN wget http://downloads.asterisk.org/pub/telephony/asterisk/asterisk-18-current.tar.gz \
    && tar zxf asterisk-18-current.tar.gz \
    && cd asterisk-18.* \
    && contrib/scripts/get_mp3_source.sh \
    && ./configure \
    && make menuselect.makeopts \
    && menuselect/menuselect \
        --enable res_config_mysql \
        --enable app_mysql \
        --enable format_mp3 \
        --enable CORE-SOUNDS-EN-WAV \
        --enable CORE-SOUNDS-EN-ULAW \
        --enable CORE-SOUNDS-EN-ALAW \
        --enable EXTRA-SOUNDS-EN-WAV \
        --enable EXTRA-SOUNDS-EN-ULAW \
        --enable EXTRA-SOUNDS-EN-ALAW \
        menuselect.makeopts \
    && make -j$(nproc) \
    && make install \
    && make samples \
    && make config \
    && ldconfig

# Create directories and set permissions
RUN mkdir -p /var/spool/asterisk/monitor \
    && mkdir -p /var/log/asterisk \
    && mkdir -p /var/run/asterisk \
    && chown -R asterisk:asterisk /var/spool/asterisk \
    && chown -R asterisk:asterisk /var/log/asterisk \
    && chown -R asterisk:asterisk /var/run/asterisk \
    && chmod -R 755 /var/spool/asterisk/monitor \
    && chown -R asterisk:asterisk /etc/asterisk \
    && chmod -R 750 /etc/asterisk

# Copy configuration files
COPY config/odbc.ini /etc/odbc.ini
COPY config/odbcinst.ini /etc/odbcinst.ini
COPY config/asterisk/ /etc/asterisk/

# Set proper ownership for copied files
RUN chown -R asterisk:asterisk /etc/asterisk \
    && chown -R asterisk:asterisk /var/*/asterisk \
    && chown -R asterisk:asterisk /usr/*/asterisk

# Expose necessary ports
EXPOSE 5060/udp
EXPOSE 10000-20000/udp

# Set entrypoint
COPY docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh

# Switch to asterisk user
USER asterisk

ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["asterisk", "-f"] 