kubectl --namespace=kube-system delete -f suiteconfig_svc.yaml
kubectl --namespace=kube-system delete -f suiteconfig.yaml
kubectl --namespace=kube-system delete -f suiteinstaller.yaml
kubectl --namespace=kube-system delete -f suite_yamls/test1.yaml
kubectl --namespace=kube-system delete -f suite_yamls/test2.yaml

rm -rf /var/vols/ITOM/suite-install

