kubectl --namespace=kube-system delete -f suiteconfig_svc.yaml
kubectl --namespace=kube-system delete -f suiteconfig.yaml
kubectl --namespace=kube-system delete -f suiteinstaller.yaml

