#!/bin/bash
# Build all microservice Docker images
for svc in post-song-microservice get-song-microservice put-song-microservice delete-song-microservice; do
  docker build -t n3vers4ydie/$svc:latest ./microservices/$svc
done