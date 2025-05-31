#!/bin/bash
# Build all microservice Docker images
echo "Building Docker images for all song microservices..."
for svc in post-song-microservice get-song-microservice put-song-microservice delete-song-microservice; do
  echo "Building $svc..."
  docker build -t n3vers4ydie/$svc:latest ./microservices/$svc
  if [ $? -ne 0 ]; then
    echo "Failed to build $svc"
    exit 1
  fi
done
echo "All images built successfully."
