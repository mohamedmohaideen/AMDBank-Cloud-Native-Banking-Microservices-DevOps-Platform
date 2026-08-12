# 🏦 AMDBank - Cloud-Native Banking Microservices & DevOps Platform

AMDBank is a cloud-native banking application built using **Java, Spring Boot, Spring Cloud, Apache Kafka, Docker, Kubernetes, Helm and Keycloak**.

The project follows a **microservices architecture**, where individual banking capabilities are developed, deployed and managed as independent services.

The goal of AMDBank is to demonstrate how a modern banking application can be designed using microservices and evolved into a production-oriented **Cloud-Native and DevOps platform**.

---

## 🚀 Project Overview

AMDBank provides banking capabilities through multiple independent microservices.

The current platform includes:

- 👤 Accounts Service
- 💳 Cards Service
- 💰 Loans Service
- 📨 Message Service
- ⚙️ Config Server
- 🌐 API Gateway
- 🔎 Kubernetes Discovery Server
- 📨 Apache Kafka
- 🔐 Keycloak
- 🗄️ H2 Database
- 🐳 Docker
- 🐙 Docker Compose
- ☸️ Kubernetes
- ⛵ Helm

The architecture is designed around:

- Microservices
- Centralized configuration
- Service discovery
- API Gateway
- Event-driven communication
- OAutH2 Database/JWT security
- Containerization
- Kubernetes orchestration
- Helm-based deployment
- Cloud-native infrastructure

---

# 🏗️ Architecture

```text
                         ┌──────────────────────┐
                         │       Client         │
                         │ Postman / Browser    │
                         └──────────┬───────────┘
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │     API Gateway      │
                         │        :8072         │
                         └──────────┬───────────┘
                                    │
                 ┌──────────────────┼──────────────────┐
                 │                  │                  │
                 ▼                  ▼                  ▼
          ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
          │  Accounts   │    │    Cards    │    │    Loans    │
          │    :8080    │    │    :9000    │    │    :8090    │
          └──────┬──────┘    └──────┬──────┘    └──────┬──────┘
                 │                  │                  │
                 └──────────────────┼──────────────────┘
                                    │
                 ┌──────────────────┼──────────────────┐
                 │                  │                  │
                 ▼                  ▼                  ▼
        ┌────────────────┐  ┌──────────────┐  ┌────────────────┐
        │ Config Server  │  │  Discovery   │  │    Keycloak    │
        │     :8071      │  │    Server    │  │      :80       │
        └────────────────┘  └──────────────┘  └────────────────┘

                           ┌───────────────┐
                           │ Apache Kafka  │
                           │     :9092     │
                           └───────┬───────┘
                                   │
                                   ▼
                           ┌───────────────┐
                           │    Message    │
                           │     :9010     │
                           └───────────────┘

🧩 Microservices
👤 Accounts Service

Responsible for customer and account-related banking operations.

Port: 8080

Responsibilities include:

Customer account management
Account information
Account-related business operations
Communication with other services when required
💳 Cards Service

Responsible for card-related banking operations.

Port: 9000

Responsibilities include:

Card information
Card-related operations
Integration with the Accounts domain
💰 Loans Service

Responsible for loan-related banking operations.

Port: 8090

Responsibilities include:

Loan information
Loan-related operations
Integration with customer/account information
📨 Message Service

Responsible for asynchronous event/message processing.

Port: 9010

The service works with Apache Kafka for event-driven communication.

⚙️ Infrastructure Services
Config Server

AMDBank uses Spring Cloud Config Server for centralized configuration management.

Port: 8071

Instead of maintaining configuration independently inside every service, configuration can be centralized through the Config Server.

Example:

SPRING_CONFIG_IMPORT=optional:configserver:http://configserver:8071/
🌐 API Gateway

The API Gateway acts as the single entry point for client requests.

Port: 8072

Instead of directly exposing every microservice to clients, requests can flow through the Gateway.

Client
   │
   ▼
Gateway :8072
   │
   ├── Accounts :8080
   ├── Cards    :9000
   └── Loans    :8090

Benefits include:

Single entry point
Request routing
Centralized security
Service abstraction
Easier integration with future service-mesh capabilities
🔎 Kubernetes Discovery Server

AMDBank uses Spring Cloud Kubernetes Discovery Server for service discovery inside Kubernetes.

Kubernetes service:

spring-cloud-kubernetes-discoveryserver

Port:

80

Internal URL:

http://spring-cloud-kubernetes-discoveryserver:80/

The discovery server allows microservices to discover other services dynamically inside the Kubernetes environment.

📨 Apache Kafka

AMDBank uses Apache Kafka for asynchronous, event-driven communication.

Kafka service:

amdbank-kafka

Kafka port:

9092

Kubernetes broker address:

amdbank-kafka-controller-headless:9092

Example architecture:

                    Banking Service
                         │
                         │ Event
                         ▼
                    Apache Kafka
                         │
              ┌──────────┴──────────┐
              │                     │
              ▼                     ▼
       Message Service        Other Consumers

Kafka enables the application to move toward an event-driven microservices architecture and reduces tight coupling between services.

🔐 Security

AMDBank uses Keycloak for authentication and OAutH2 Database/JWT-based security.

Keycloak provides:

User authentication
Access token generation
OAutH2 Database/OpenID Connect support
JWT-based authorization

Authentication flow:

Client
   │
   │ Login
   ▼
Keycloak
   │
   │ Access Token
   ▼
Client
   │
   │ Bearer Token
   ▼
API Gateway / Microservice
   │
   ▼
JWT Validation

Keycloak JWK endpoint used by the application:

http://keycloak.default.svc.cluster.local:80/realms/master/protocol/openid-connect/certs

⚠️ Never commit real passwords, client secrets, access tokens or production credentials to GitHub.

🗄️ Database

AMDBank uses relational databases for persistent data.

The Kubernetes environment includes:

H2 Database
Port: 5432

For development/testing, H2 Database may also be used depending on the application configuration.

Database credentials should be supplied using:

Environment variables
Kubernetes Secrets
External secret management systems

Sensitive values should never be hardcoded into source code.

🐳 Docker

AMDBank services are designed to run as containers.

Docker is used for:

Containerizing Spring Boot services
Creating consistent development environments
Running infrastructure locally
Running microservices independently
Preparing applications for Kubernetes deployment

Docker Compose configurations are also included in the project.

🐙 Docker Compose

Docker Compose can be used to run multiple services and infrastructure components locally.

Start the environment:

docker compose up -d

Check running containers:

docker ps

Stop the environment:

docker compose down
☸️ Kubernetes

AMDBank is deployed to Kubernetes for container orchestration.

The Kubernetes environment contains components such as:

Deployments
StatefulSets
Services
ConfigMaps
Secrets
Persistent storage
Kafka
H2 Database
Keycloak
Config Server
Discovery Server
API Gateway

High-level Kubernetes architecture:

                    Kubernetes Cluster
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ▼                ▼                ▼
       Gateway        Microservices     Infrastructure
        :8072              │                │
                           │                ├── Kafka
            ┌──────────────┼────────────┐   ├── H2 Database
            │              │            │   ├── Keycloak
            ▼              ▼            ▼   ├── Config Server
        Accounts         Cards        Loans └── Discovery
⛵ Helm

Kubernetes deployments are managed using Helm.

Helm provides:

Reusable Kubernetes templates
Environment-specific configuration
Dependency management
Easier deployments
Easier upgrades
Versioned application releases

Project Helm structure:

kubernetes/
└── helm/
    ├── amdbank-common/
    ├── amdbank-services/
    └── environments/
        └── dev-env/

Build dependencies:

helm dependency build

Install AMDBank:

helm install amdbank .

Upgrade AMDBank:

helm upgrade amdbank .

Check Helm releases:

helm list

Check release status:

helm status amdbank
📦 Kubernetes Services
Service	Port	Type
Accounts	8080	ClusterIP
Cards	9000	ClusterIP
Loans	8090	ClusterIP
Message	9010	ClusterIP
Config Server	8071	ClusterIP
Gateway	8072	LoadBalancer
Keycloak	80	LoadBalancer
Kafka	9092	ClusterIP
H2 Database	5432	ClusterIP
Discovery Server	80	ClusterIP
🔌 API Endpoints

AMDBank exposes REST APIs through the individual microservices and API Gateway.

The exact endpoint paths should be maintained according to the controllers implemented in each service.

Accounts API

Base URL:

http://localhost:8080

Gateway:

http://localhost:8072
Method	Endpoint	Description
GET	/api/accounts/...	Account operations
POST	/api/accounts/...	Create account
PUT	/api/accounts/...	Update account
DELETE	/api/accounts/...	Delete account
Cards API

Base URL:

http://localhost:9000

Gateway:

http://localhost:8072
Method	Endpoint	Description
GET	/api/cards/...	Card operations
POST	/api/cards/...	Create card
PUT	/api/cards/...	Update card
DELETE	/api/cards/...	Delete card
Loans API

Base URL:

http://localhost:8090

Gateway:

http://localhost:8072
Method	Endpoint	Description
GET	/api/loans/...	Loan operations
POST	/api/loans/...	Create loan
PUT	/api/loans/...	Update loan
DELETE	/api/loans/...	Delete loan

Replace the example /api/... paths above with the exact mappings from the controllers before treating this section as the definitive API documentation.

❤️ Health Checks

Spring Boot Actuator is used for application health monitoring.

Example:

GET /actuator/health

Example response:

{
  "status": "UP"
}

Health endpoints can also be used by Kubernetes for:

Liveness probes
Readiness probes
Application health monitoring
🔍 Kubernetes Troubleshooting

Check all pods:

kubectl get pods

Check services:

kubectl get svc

Check deployments:

kubectl get deployments

Check StatefulSets:

kubectl get statefulsets

Check ConfigMaps:

kubectl get configmaps

Check logs:

kubectl logs deployment/accounts-deployment

Enter a running container:

kubectl exec -it deployment/accounts-deployment -- sh

Test Discovery Server DNS:

getent hosts spring-cloud-kubernetes-discoveryserver

Test Discovery Server:

curl http://spring-cloud-kubernetes-discoveryserver:80/actuator/health

Test Kafka DNS:

getent hosts amdbank-kafka-controller-headless

Test Kafka connectivity:

curl -v telnet://amdbank-kafka-controller-headless:9092
⚙️ Configuration Management

Application configuration is managed using environment-specific configuration and Kubernetes ConfigMaps.

Example configuration:

SPRING_PROFILES_ACTIVE=default
SPRING_CONFIG_IMPORT=optional:configserver:http://configserver:8071/
SPRING_CLOUD_STREAM_KAFKA_BINDER_BROKERS=amdbank-kafka-controller-headless:9092

Sensitive configuration should be provided through environment variables or Kubernetes Secrets.

Example:

env:
  - name: DB_USERNAME
    valueFrom:
      secretKeyRef:
        name: amdbank-secret
        key: DB_USERNAME

  - name: DB_PASSWORD
    valueFrom:
      secretKeyRef:
        name: amdbank-secret
        key: DB_PASSWORD
🧪 Testing

The APIs can be tested using:

Postman
curl
IntelliJ HTTP Client

Example health check:

curl http://localhost:8072/actuator/health

Example Kubernetes health check:

kubectl exec -it deployment/accounts-deployment -- sh

Then:

curl http://spring-cloud-kubernetes-discoveryserver:80/actuator/health

🛠️ Technology Stack
Backend
Java
Spring Boot
Spring Cloud
Spring Data JPA
Spring Security
OAutH2 Database
JWT
REST APIs
Microservices
Accounts
Cards
Loans
Message
Configuration & Discovery
Spring Cloud Config
Spring Cloud Kubernetes Discovery
Messaging
Apache Kafka
Spring Cloud Stream
Security
Keycloak
OAutH2 Database
JWT
Database
H2 Database
Containerization
Docker
Docker Compose
Orchestration
Kubernetes
Helm
Development
IntelliJ IDEA
Maven
Git
GitHub
Postman
