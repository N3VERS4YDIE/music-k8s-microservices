./build-images.sh

kind delete cluster --name music
kind create cluster --name music --config k8s/kind-config.yaml

# for svc in post-song-microservice get-song-microservice put-song-microservice delete-song-microservice; do
#   kind load docker-image n3vers4ydie/$svc:latest --name music
# done

for svc in post-song-microservice get-song-microservice put-song-microservice delete-song-microservice; do
  docker build -t n3vers4ydie/$svc:latest ./microservices/$svc
  docker push n3vers4ydie/$svc:latest
done

kubectl config use-context kind-music
kubectl label node music-control-plane ingress-ready=true
kubectl apply -f k8s/ingress-nginx.yaml
kubectl wait --namespace ingress-nginx --for=condition=ready pod --selector=app.kubernetes.io/component=controller --timeout=90s
kubectl apply -f k8s/
kubectl get pods -n ingress-nginx
kubectl get pods -o wide
kubectl get services
kubectl get deployments
kubectl get ingress

# kubectl delete all --all --force --grace-period=0
# kubectl delete all,ingress --all --force --grace-period=0