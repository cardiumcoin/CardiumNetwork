FROM openjdk:11-jre-slim

ENV WAVES_LOG_LEVEL=INFO
ENV WAVES_HEAP_SIZE=2g
ENV WAVES_NETWORK=mainnet

ENV YOURKIT_VERSION=2022.9

SHELL ["/bin/bash", "-c"]

# Additional dependencies
RUN apt-get update && apt-get install -y wget unzip gosu || exit 1; \
    export YOURKIT_ARCHIVE="YourKit-JavaProfiler-$YOURKIT_VERSION-docker.zip"; \
    wget --quiet "https://www.yourkit.com/download/docker/$YOURKIT_ARCHIVE" -P /tmp/ && unzip /tmp/$YOURKIT_ARCHIVE -d /usr/local || exit 1; \
    # Clean
    apt-get remove -y wget unzip && apt-get autoremove -y && apt-get autoclean && rm -rf /var/lib/apt/lists/*

# Node files
ENV WVDATA=/var/lib/cardium
ENV WVLOG=/var/log/cardium
ENV WAVES_INSTALL_PATH=/usr/share/cardium
ENV WAVES_CONFIG=/etc/cardium/CARDIUM.conf
COPY target /tmp/
COPY waves.conf.template $WAVES_CONFIG

# Setup node
COPY entrypoint.sh $WAVES_INSTALL_PATH/bin/entrypoint.sh
ARG INCLUDE_GRPC=true
RUN mkdir -p $WVDATA $WVLOG; \
    # Create user
    groupadd -r cardium --gid=999; \
    useradd -r -g cardium --uid=999 --home-dir=$WVDATA --shell=/bin/bash cardium; \
    # Unpack tgz packages
    tar zxvf /tmp/cardium.tgz -C $WAVES_INSTALL_PATH --strip-components=1; \
    if [[ $INCLUDE_GRPC == true ]]; then \
      echo "Unpacking gRPC server"; \
      tar zxvf /tmp/cardium-grpc-server.tgz -C $WAVES_INSTALL_PATH --strip-components=1; \
    fi; \
    # Set permissions
    chown -R cardium:cardium $WVDATA $WVLOG $WAVES_INSTALL_PATH && chmod 755 $WVDATA $WVLOG; \
    # Cleanup
    rm -rf /tmp/*

EXPOSE 6859 6860 6560 6862 6870
VOLUME $WVDATA
VOLUME $WVLOG
VOLUME $WAVES_INSTALL_PATH/lib/plugins
WORKDIR $WVDATA


STOPSIGNAL SIGINT
ENTRYPOINT ["/usr/share/cardium/bin/entrypoint.sh"]
