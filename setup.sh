#!/bin/bash
# ./build-images.sh

# Push Docker images to Docker Hub:
# for svc in post-song-microservice get-song-microservice put-song-microservice delete-song-microservice; do
#   docker push n3vers4ydie/$svc:latest
# done

# Load Docker images into Kind cluster if dont want to push to Docker Hub:
# for svc in post-song-microservice get-song-microservice put-song-microservice delete-song-microservice; do
#   kind load docker-image n3vers4ydie/$svc:latest --name music
# done

kind delete cluster --name music
kind create cluster --name music --config kind-config.yaml

kubectl config use-context kind-music
kubectl label node music-control-plane ingress-ready=true
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.9.6/deploy/static/provider/kind/deploy.yaml
kubectl wait --namespace ingress-nginx --for=condition=ready pod --selector=app.kubernetes.io/component=controller --timeout=90s
kubectl apply -f k8s/
kubectl get pods -o wide -n ingress-nginx
kubectl get pods -o wide
kubectl get services
kubectl get deployments
kubectl get ingress

# Cleanup commands:
# kubectl delete all --all --force --grace-period=0
# kubectl delete all,ingress --all --force --grace-period=0
# kubectl delete namespace ingress-nginx
# kubectl delete clusterrole ingress-nginx
# kubectl delete clusterrolebinding ingress-nginx