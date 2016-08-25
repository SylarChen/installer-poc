echo "API_SERVER_IP: " $API_SERVER_IP > /createpod.log

echo "API_SERVER_PORT: " $API_SERVER_PORT >> /createpod.log

curl -H "Content-Type: application/yaml" -X POST http://$API_SERVER_IP:$API_SERVER_PORT/api/v1/namespaces/kube-system/pods -d "$(cat /pv_suite_install/itsma/suiteconfig.yaml)" >> /createpod.log

curl -H "Content-Type: application/yaml" -X POST http://$API_SERVER_IP:$API_SERVER_PORT/api/v1/namespaces/kube-system/services -d "$(cat /pv_suite_install/itsma/suiteconfig_svc.yaml)" >> /createpod.log
