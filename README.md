# Cardium

The Cardium Coin Blockchain is redefining the blockchain landscape with an infrastructure designed to set new standards for speed and scalability. With a current capacity of 1,000 transactions per second (tx/s) and block generation every 10 seconds, Cardium already delivers a level of performance that few can match. However, with the release of Cardium 2.0, we are set to elevate this network to unprecedented processing power: a capacity of 100,000 tx/s with block generation in just 1 second. This is not merely a promise but a reality in progress.

Our commitment extends beyond the immediate upgrade. The Cardium Coin Blockchain is built with a modular and adaptive architecture, structured to scale progressively up to 4,000,000 tx/s, meeting the demands of even the most intensive projects. To achieve this benchmark, we are implementing a suite of cutting-edge technologies carefully engineered to maximize network efficiency and resilience:

Dynamic and Intelligent Sharding: Cardium adopts a dynamic sharding system that distributes the transactional load across multiple independent clusters, with intelligent and adaptive allocation to balance performance in real time, preserving both network efficiency and security.

Parallel Transaction Execution and Multilayered Infrastructure: We utilize a multi-layered architecture that enables parallel transaction processing, eliminating traditional bottlenecks and dramatically boosting throughput. The network can handle complex transaction flows across multiple simultaneous layers, ensuring data consistency and integrity.

Low-Latency Hybrid Consensus Protocol: We have developed an innovative, real-time adjustable consensus protocol that minimizes latency and optimizes confirmation times without sacrificing security. This hybrid protocol integrates multiple consensus approaches, dynamically adapting to real-time transactional demand.

Data Compression and Transaction Aggregation: Our network leverages data compression and aggregation algorithms that allow for optimal block density. This technology enables massive data processing without a significant increase in block size, maximizing storage efficiency and confirmation speed.

Every technical detail of Cardium has been engineered for agile, secure, and highly efficient implementation, ensuring that the network maintains consistent performance and is ready for the exponential demand that Web3 and blockchain technologies demand. The Cardium Coin Blockchain not only surpasses the current industry limits; it sets a new standard for the next generation of decentralized infrastructure.

By choosing Cardium, you are investing in technology poised to transform entire markets and industries. Our robust and scalable infrastructure creates opportunities for an ecosystem of innovation that extends beyond what was previously possible, positioning Cardium as the definitive platform for any application requiring superior performance, unmatched security, and unprecedented scalability.


# Installation

Please read [repo wiki article](https://docs.cardium.network/nodes/how-to-setup-a-new-node.html).


## 👨‍💻 Development

```
set ThisBuild/network := Testnet

set ThisBuild/network := Mainnet
```

The node can be built and installed wherever Java can run. 
To build and test this project, you will have to follow these steps:

<details><summary><b>Show instructions</b></summary>

*1. Setup the environment.*
- Install Java for your platform:

```bash
sudo apt-get update
sudo apt-get install openjdk-8-jre                     # Ubuntu
# or
# brew cask install adoptopenjdk/openjdk/adoptopenjdk8 # Mac
```

- Install SBT (Scala Build Tool)

Please follow the SBT installation instructions depending on your platform ([Linux](https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Linux.html), [Mac](https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Mac.html), [Windows](https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Windows.html))

*2. Clone this repo*

```bash
git clone https://github.com/cardiumcoin/CardiumNetwork.git
cd CardiumNetwork
```

*3. Compile and run tests*

```bash
sbt --mem 6144 --batch checkPR
```

*4. Run integration tests (optional)*

Create a Docker image before you run any test: 
```bash
sbt node-it/docker
```

- Run all tests. You can increase or decrease number of parallel running tests by changing `SBT_THREAD_NUMBER`
```bash
SBT_THREAD_NUMBER=4 sbt node-it/test
```

- Run one test:
```bash
sbt node-it/testOnly *.TestClassName
# or 
# bash node-it/testOnly full.package.TestClassName
```

*5. Build packages* 

```bash
sbt packageAll                   # Mainnet
sbt -Dnetwork=testnet packageAll # Testnet
```

`sbt packageAll` ‌produces only `deb` package along with a fat `jar`. 

*6. Install DEB package*

`deb` package is located in target folder. You can replace '*' with actual package name:

```bash
sudo dpkg -i node/target/*.deb
```


*7. Run an extension project locally during development (optional)*

```bash
sbt "extension-module/run /path/to/configuration"
```

*8. Configure IntelliJ IDEA (optional)*

The majority of contributors to this project use IntelliJ IDEA for development, if you want to use it as well please follow these steps:

1. Click `Add configuration` (or `Edit configurations...`).
2. Click `+` to add a new configuration, choose `Application`.
3. Specify:
   - Main class: `com.wavesplatform.Application`
   - Program arguments: `/path/to/configuration`
   - Use classpath of module: `extension-module`
4. Click `OK`.
5. Run this configuration.

</details>

## 👏 Acknowledgements


Special thanks to wavesplatform devs for writing the base code, and the support.
