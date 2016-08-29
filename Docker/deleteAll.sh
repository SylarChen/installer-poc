kubectl --namespace=kube-system delete -f suiteconfig_svc.yaml
kubectl --namespace=kube-system delete -f suiteconfig.yaml
kubectl --namespace=kube-system delete -f suiteinstaller.yaml
kubectl --namespace=kube-system delete -f suite_yamls/test.yaml
kubectl --namespace=kube-system delete -f suite_yamls/test_svc.yaml
kubectl --namespace=kube-system delete -f suite_yamls/configmap.yaml

rm -rf /var/vols/ITOM/suite-install

