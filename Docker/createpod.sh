echo "API_SERVER_IP: " $API_SERVER_HOST > /createpod.log

echo "API_SERVER_PORT: " $API_SERVER_PORT >> /createpod.log

curl -H "Content-Type: application/yaml" -X POST http://$API_SERVER_HOST:$API_SERVER_PORT/api/v1/namespaces/kube-system/configmaps -d "$(cat /pv_suite_install/itsma/suiteconfig_cm.yaml)" >> /createpod.log

curl -H "Content-Type: application/yaml" -X POST http://$API_SERVER_HOST:$API_SERVER_PORT/api/v1/namespaces/kube-system/services -d "$(cat /pv_suite_install/itsma/suiteconfig_svc.yaml)" >> /createpod.log

curl -H "Content-Type: application/yaml" -X POST http://$API_SERVER_HOST:$API_SERVER_PORT/apis/extensions/v1beta1/namespaces/kube-system/ingresses -d "$(cat /pv_suite_install/itsma/suiteconfig_ing.yaml)" >> /createpod.log

curl -H "Content-Type: application/yaml" -X POST http://$API_SERVER_HOST:$API_SERVER_PORT/api/v1/namespaces/kube-system/pods -d "$(cat /pv_suite_install/itsma/suiteconfig.yaml)" >> /createpod.log

#curl http://$API_SERVER_HOST:$API_SERVER_PORT/api/v1/namespaces/kube-system/services/suite-conf-svc >> /createpod.log
