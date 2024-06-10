# NBA-stat Project

## Overview
This project is a microservices-based application for managing NBA statistics. The application consists of several services, each responsible for a specific part of the functionality. All services are registered with Eureka for service discovery and use Feign clients for inter-service communication. The API Gateway is used to route all incoming requests to the appropriate services. The application uses PostgreSQL as the primary database and Redis for caching.

## Services

1. **analytic-service**: Handles statistical analysis of players and teams. This is the main microservice with the required logic, retrieving average statistics for a player or team and storing the data in the cache. When new data is added in the statistics service (service 5), the cache for the keys team+season and player+season is cleared.
2. **api-gateway**: Acts as the gateway for all incoming requests, routing them to the appropriate services.
3. **eureka-server**: Manages service registration and discovery.
4. **player-service**: Manages player-related data.
5. **statistics-service**: Manages the statistics of players and teams.
6. **team-service**: Manages team-related data.

### Getting Started

### Prerequisites
- Docker
- Docker Compose

### Starting the Application with Docker

1. **Clone the repository**:
   ```bash
   git clone [<repository-url>](https://github.com/nmalinchik/nba-stat.git)
   cd NBA-stat
   ```

2. **Build the JAR files for each microservice**:
   For convenience, there are two scripts located at the root of the project, `build_all.bat` for Windows and `build_all.sh` for Unix systems, which will iterate over all services and generate the JAR files.
   - For Windows:
     ```bash
     ./build_all.bat
     ```
   - For Unix:
     ```bash
     ./build_all.sh
     ```

3. **Build and start the services**:
   Navigate to the directory `./docker` where the `docker-compose.yml` file is located and run the following command:
   ```bash
   docker-compose up --build
   ```

4. **Access the services**:
   - **Eureka Server**: [http://localhost:8761](http://localhost:8761)
   - **API Gateway**: [http://localhost:8080](http://localhost:8080)
   - **Analytic Service**: [http://localhost:8081](http://localhost:8081), [http://localhost:8082](http://localhost:8082), [http://localhost:8083](http://localhost:8083)
   - **Player Service**: [http://localhost:8084](http://localhost:8084)
   - **Statistics Service**: [http://localhost:8085](http://localhost:8085), [http://localhost:8086](http://localhost:8086)
   - **Team Service**: [http://localhost:8087](http://localhost:8087)


5. **Initialize the database with test data**:
   Using Flyway, the database is populated with test data, including tables for teams, players, and statistics. This includes approximately 40 players in 4 teams, with around 700 statistical records.

### Common Issues

#### 1. Service Registration
If a service fails to register with Eureka, ensure that:
- The Eureka server is running.
- The service's `application.yml` or `application.properties` file has the correct Eureka server URL.

#### 2. Feign Client Connection Refused
If a Feign client throws a `Connection refused` error, ensure that:
- The target service is up and running.
- The service name in the `@FeignClient` annotation matches the registered service name in Eureka.
