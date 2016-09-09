kubectl --namespace=kube-system delete -f suiteconfig_svc.yaml
kubectl --namespace=kube-system delete -f suiteconfig_cm.yaml
kubectl --namespace=kube-system delete -f suiteconfig_ing.yaml
kubectl --namespace=kube-system delete -f suiteconfig.yaml
kubectl --namespace=kube-system delete -f suiteinstaller.yaml
kubectl --namespace=itsma delete -f suite_yamls/test.yaml
kubectl --namespace=itsma delete -f suite_yamls/test_svc.yaml
kubectl --namespace=itsma delete -f suite_yamls/configmap.yaml
kubectl --namespace=kube-system delete -f suite_yamls/test.yaml
kubectl --namespace=kube-system delete -f suite_yamls/test_svc.yaml
kubectl --namespace=kube-system delete -f suite_yamls/configmap.yaml


kubectl delete -f suite_yamls/am_configmap.yaml
kubectl delete -f suite_yamls/amsuite.yaml

rm -rf /var/vols/ITOM/suite-install

