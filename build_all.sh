#!/bin/bash

services=("analytic-service" "api-gateway" "eureka-server" "player-service" "statistics-service" "team-service")


for service in "${services[@]}"; do
  echo "Building $service..."
  (cd $service && mvn clean package)
  if [ $? -ne 0 ]; then
    echo "Build failed for $service"
    exit 1
  fi
  echo "$service built successfully"
done

echo "All services built successfully"
