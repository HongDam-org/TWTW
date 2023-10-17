#bin/bash

kubectl apply -f ../k8s/ingress
kubectl apply -f ../k8s
kubectl kustomize ../k8s/database | kubectl apply -f -
kubectl kustomize ../k8s/redis | kubectl apply -f -
kubectl kustomize ../k8s/backend | kubectl apply -f -
