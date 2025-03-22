# Talkative Project

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7-green)
![Kubernetes](https://img.shields.io/badge/Kubernetes-Deployment-blue)
![Docker](https://img.shields.io/badge/Docker-Image%20Available-blue)

## Introduction
Talakative is a Java Spring Boot application built for scalable and efficient communication services. It leverages Kubernetes for seamless deployment and auto-scaling, ensuring high performance and fault tolerance. Designed with a fully microservices architecture, Talakative utilizes Spring Cloud Gateway for API routing and Netflix Eureka for service discovery. The application is optimized for performance with Project Reactor, improving API response times by over 92%, and Kafka enables efficient, event-driven communication between microservices.

The system is resilient, employing Resilience4J for fault tolerance and Spring Boot Security with JWT for secure authentication. Centralized logging is managed via SLF4J, while various databases like Redis, PostgreSQL, MySQL, and MongoDB are Dockerized and integrated using Reactive Programming for asynchronous data handling

## Prerequisites
Ensure the following are installed before deploying Talkative:

- **Kubernetes**: `kubectl` & Minikube for local deployment or a cloud-based cluster.
- **Docker**
- **Java**: JDK 17 or later.
- **Maven**

## Setting Up Kubernetes Cluster
1. Install [Minikube](https://minikube.sigs.k8s.io/docs/start/) for local testing or use a cloud provider.
2. Start Minikube:
    ```sh
    minikube start
    ```
3. Verify the cluster status:
    ```sh
    kubectl cluster-info
    ```

## Pulling or Building Docker Images

### Option 1: Pull from Docker Hub
Pull pre-built images for all microservices:
```sh
docker pull dhruv21patel/microservice1:latest
docker pull dhruv21patel/microservice2:latest
...
```

### Option 2: Build from Source
1. Package the application:
    ```sh
    mvn clean package
    ```
2. Build and tag Docker images:
    ```sh
    docker build -t microservice1:latest ./microservice1
    docker build -t microservice2:latest ./microservice2
    ...
    ```
3. Push images to Docker Hub (if required):
    ```sh
    docker tag microservice1:latest <dockerhub-username>/microservice1:latest
    docker push <dockerhub-username>/microservice1:latest

    docker tag microservice2:latest <dockerhub-username>/microservice2:latest
    docker push <dockerhub-username>/microservice2:latest
    ...
    ```

## Deploying Talkative on Kubernetes

The Depoyment files are given under the Kube folder. For each microservice there is a Depoyment,Scaling and Node folder which Deploys and make it available to other pods and node with service.yml also add the namespace according to your need , Make Sure to add NameSpace for Each microservice so other Services cannot access Databases other then that Service from that Node.

1. Apply deployment configurations:
    ```sh
    kubectl apply -f microservice1/deployment.yml
    kubectl apply -f microservice2/deployment.yml
    ...
    ```
2. Apply service configurations:
    ```sh
    kubectl apply -f microservice1/service.yml
    kubectl apply -f microservice2/service.yml
    ...
    ```
3. Verify the deployment:
    ```sh
    kubectl get pods
    kubectl get services
    ```

## Scaling the Application
### Using Scaling Configurations
```sh
kubectl apply -f microservice1/scaling.yml
kubectl apply -f microservice2/scaling.yml
...
```

### Manual Scaling
```sh
kubectl scale deployment microservice1 --replicas=3
kubectl scale deployment microservice2 --replicas=3
...
```

## Accessing the Services
Retrieve service URLs:
```sh
minikube service microservice1 --url
minikube service microservice2 --url
...
```
For cloud deployments, use:
```sh
kubectl get svc
```

## Conclusion
Talkative provides a scalable Spring Boot-based solution with Kubernetes. Use the provided YAML configurations to manage deployments efficiently for each microservice.
