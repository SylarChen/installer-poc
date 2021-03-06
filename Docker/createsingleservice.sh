date >> /pod.log

echo "API_SERVER_IP: " $API_SERVER_IP >> /pod.log

echo "API_SERVER_PORT: " $API_SERVER_PORT >> /pod.log

echo "$(cat $1)" >> /pod.log

curl -H "Content-Type: application/yaml" -X POST http://$API_SERVER_IP:$API_SERVER_PORT/api/v1/namespaces/$2/services -d "$(cat $1)" >> /pod.log

echo "-------------------------------"
