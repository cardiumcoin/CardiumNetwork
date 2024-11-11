# Cardium Node in Docker

## About Cardium
Cardium is a decentralized platform that allows any user to issue, transfer, swap and trade custom blockchain tokens on an integrated peer-to-peer exchange. You can find more information about Cardium at [cardium.network](https://cardium.network/) and in the official [documentation](https://docs.cardium.network).


## About the image
This Docker image is focused on fast and convenient deployment of Cardium Node.
The image contains scripts and configs to run Cardium Node for `mainnet`, `testnet` or `stagenet` networks.
If you need to run node in private network, see [Cardium private node](https://github.com/cardiumcoin/CardiumNetwork/tree/master/docker#cardium-private-node) section.

GitHub repository: https://github.com/cardiumcoin/CardiumNetwork/tree/master/docker

## Prerequisites
It is highly recommended to read more about [Cardium Node configuration](https://docs.cardium.network/en/cardium-node/node-configuration) before running the container.

## Building Docker image
`./build-with-docker.sh && docker build -t cardiumcoin/cardium docker` (from the repository root) - builds an image with the current local repository

**You can specify following arguments when building the image:**


| Argument          | Default value | Description                                                                                                                                                                                                                                                                                   |
|-------------------|---------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `INCLUDE_GRPC`    | `true`        | Whether to include gRPC server files in the image.                                                                                                                                                                                                                                            |

**Note: All build arguments are optional.**

## Running Docker image

### Configuration options

1. The image supports Cardium Node config customization. To change a config field use corresponding JVM options. JVM options can be sent to JVM using `JAVA_OPTS` environment variable. Please refer to ([complete configuration file](https://github.com/cardiumcoin/CardiumNetwork/blob/master/node/src/main/resources/application.conf)) to get the full path of the configuration item you want to change.

```
docker run -v /docker/cardium/cardium-data:/var/lib/cardium -v /docker/cardium/cardium-config:/etc/cardium -p 6869:6869 -p 6862:6862 -e JAVA_OPTS="-Dwaves.rest-api.enable=yes -Dwaves.wallet.password=myWalletSuperPassword" -ti cardiumcoin/cardium
```

2. Cardium Node is looking for a config in the directory `/etc/cardium/cardium.conf` which can be mounted using Docker volumes. During image build, a default configuration will be copied to this directory. While running container if the value of `WAVES_NETWORK` is not `mainnet`, `testnet` or `stagenet`, default configuration won't be enough for correct node working. This is a scenario of using `CUSTOM` network - correct configuration must be provided when running container. If you use `CUSTOM` network and `/etc/cardium/cardium.conf` is NOT found Cardium Node container will exit.

3. By default, `/etc/cardium/cardium.conf` config includes `/etc/cardium/local.conf`. Custom `/etc/cardium/local.conf` can be used to override default config entries. Custom `/etc/cardium/cardium.conf` can be used to override or the whole configuration. For additional information about Docker volumes mapping please refer to `Managing data` item.

### Environment variables

**You can run container with predefined environment variables:**

| Env variable                      | Description  |
|-----------------------------------|--------------|
| `WAVES_WALLET_SEED`        		| Base58 encoded seed. Overrides `-Dwaves.wallet.seed` JVM config option. |
| `WAVES_WALLET_PASSWORD`           | Password for the wallet file. Overrides `-Dwaves.wallet.password` JVM config option. |
| `WAVES_LOG_LEVEL`                 | Node logging level. Available values: `OFF`, `ERROR`, `WARN`, `INFO`, `DEBUG`, `TRACE`. More details about logging are available [here](https://docs.cardium.network/en/cardium-node/logging-configuration).|
| `WAVES_HEAP_SIZE`                 | Default Java Heap Size limit in -X Command-line Options notation (`-Xms=[your value]`). More details [here](https://docs.oracle.com/cd/E13150_01/jrockit_jvm/jrockit/jrdocs/refman/optionX.html). |
|`WAVES_NETWORK`                    | Cardium Blockchain network. Available values are `mainnet`, `testnet`, `stagenet`.|
|`JAVA_OPTS`                        | Additional Cardium Node JVM configuration options. 	|

**Note: All variables are optional.**  

**Note: Environment variables override values in the configuration file.** 

### Managing data
We recommend to store the blockchain state as well as Cardium configuration on the host side. As such, consider using Docker volumes mapping to map host directories inside the container:

**Example:**

1. Create a directory to store Cardium data:

```
mkdir -p /docker/cardium
mkdir /docker/cardium/cardium-data
mkdir /docker/cardium/cardium-config
```

Once container is launched it will create:

- three subdirectories in `/docker/cardium/cardium-data`:
```
/docker/cardium/cardium-data/log    - Cardium Node logs
/docker/cardium/cardium-data/data   - Cardium Blockchain state
/docker/cardium/cardium-data/wallet - Cardium Wallet data
```
- `/docker/cardium/cardium-config/cardium.conf` - default Cardium config


3. If you already have Cardium Node configuration/data - place it in the corresponsing directories

4. Add the appropriate arguments to ```docker run``` command: 
```
docker run -v /docker/cardium/cardium-data:/var/lib/cardium -v /docker/cardium/cardium-config:/etc/cardium -e WAVES_NETWORK=stagenet -e WAVES_WALLET_PASSWORD=myWalletSuperPassword -ti cardiumcoin/cardium
```

### Blockchain state

If you are a Cardium Blockchain newbie and launching Cardium Node for the first time be aware that after launch it will start downloading the whole blockchain state from the other nodes. During this download it will be verifying all blocks one after another. This procesure can take some time.

You can speed this process up by downloading a compressed blockchain state from our official resources, extract it and mount inside the container (as discussed in the previous section). In this scenario Cardium Node skips block verifying. This is a reason why it takes less time. This is also a reason why you must download blockchain state *only from our official resources*.

**Note**: We do not guarantee the state consistency if it's downloaded from third-parties.

|Network     |Link          |
|------------|--------------|
|`mainnet`   | http://blockchain.cardiums.com/blockchain_last.tar |
|`testnet`   | http://blockchain-testnet.cardiums.com/blockchain_last.tar  |
|`stagenet`  | http://blockchain-stagenet.cardiums.com/blockchain_last.tar |


**Example:**
```
mkdir -p /docker/cardium/cardium-data

wget -qO- http://blockchain-stagenet.cardiums.com/blockchain_last.tar --show-progress | tar -xvf - -C /docker/cardium/cardium-data

docker run -v /docker/cardium/cardium-data:/var/lib/cardium cardiumcoin/Node -e WAVES_NETWORK=stagenet -e WAVES_WALLET_PASSWORD=myWalletSuperPassword -ti cardiumcoin/cardium
```

### Network Ports

1. REST-API interaction with Node. Details are available [here](https://docs.cardium.network/en/cardium-node/node-configuration#rest-api-settings).

2. Cardium Node communication port for incoming connections. Details are available [here](https://docs.cardium.network/en/cardium-node/node-configuration#network-settings).


**Example:**
Below command will launch a container:
- with REST-API port enabled and configured on the socket `0.0.0.0:6870`
- Cardium node communication port enabled and configured on the socket `0.0.0.0:6868`
- Ports `6868` and `6870` mapped from the host to the container

```
docker run -v /docker/cardium/cardium-data:/var/lib/cardium -v /docker/cardium/cardium-config:/etc/cardium -p 6870:6870 -p 6868:6868 -e JAVA_OPTS="-Dwaves.network.declared-address=0.0.0.0:6868 -Dwaves.rest-api.port=6870 -Dwaves.rest-api.bind-address=0.0.0.0 -Dwaves.rest-api.enable=yes" -e WAVES_WALLET_PASSWORD=myWalletSuperPassword -e WAVES_NETWORK=stagenet -ti cardiumcoin/cardium
```

Check that REST API is up by navigating to the following URL from the host side:
http://localhost:6870/api-docs/index.html

### Extensions
You can run custom extensions in this way:
1. Copy all lib/*.jar files from extension to any directory, lets say `plugins`
2. Add extension class to configuration file, lets say `local.conf`, located in `config` directory containing also `cardium.conf`:
```hocon
waves.extensions += com.johndoe.WavesExtension
```
3. Run `docker run -v "$(pwd)/plugins:/usr/share/cardium/lib/plugins" -v "$(pwd)/config:/etc/cardium" -i cardiumcoin/cardium`

## Cardium private node

The image is useful for developing dApps and other smart contracts on Cardium blockchain.

### Getting started

To run the node,\
`docker run -d --name cardium-private-node -p 6869:6869 cardiumcoin/cardium-private-node`

To view node API documentation, open http://localhost:6869/

### Preserve blockchain state

If you want to keep the blockchain state, then just stop the container instead of killing it, and start it again when needed:\
`docker stop cardium-private-node`
`docker start cardium-private-node`

### Configuration details

The node is configured with:

- faster generation of blocks (**10 sec** interval)
- all features pre-activated
- custom chain id - **R**
- api_key `cardium-private-node`
- default miner account with all Cardium tokens (you can distribute these tokens to other accounts as you wish):
  ```
  rich account:
      Seed text:           cardium private node seed with cardium tokens
      Seed:                TBXHUUcVx2n3Rgszpu5MCybRaR86JGmqCWp7XKh7czU57ox5dgjdX4K4
      Account seed:        HewBh5uTNEGLVpmDPkJoHEi5vbZ6uk7fjKdP5ghiXKBs
      Private account key: 83M4HnCQxrDMzUQqwmxfTVJPTE9WdE7zjAooZZm2jCyV
      Public account key:  AXbaBkJNocyrVpwqTzD4TpUY8fQ6eeRto9k1m2bNCzXV
      Account address:     3M4qwDomRabJKLZxuXhwfqLApQkU592nWxF
  ```

Full node configuration is available on Github in `cardium.custom.conf`: https://github.com/cardiumcoin/CardiumNetwork/blob/master/docker/private/cardium.custom.conf

### Image tags

You can use the following tags:

- `latest` - currrent version of Mainnet
- `vX.X.X` - specific version of Cardium Node
