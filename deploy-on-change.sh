#!/bin/bash
# Watch for code changes and redeploy microservices to Kubernetes

MICROSERVICES=(post-song-microservice get-song-microservice put-song-microservice delete-song-microservice)

while true; do
  inotifywait -r -e modify,create,delete ./microservices
  echo "Code change detected. Building images and applying Kubernetes manifests..."
  ./build-images.sh
  kubectl apply -f k8s/
  echo "Deployment updated."
  sleep 2
done
