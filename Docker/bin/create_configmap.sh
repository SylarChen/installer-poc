API_SERVER_HOST=$1
API_SERVER_PORT=$2
K8S_NAMESPACE=$3
YAML_FILE=$4

URL=http://$API_SERVER_HOST:$API_SERVER_PORT/api/v1/namespaces/$K8S_NAMESPACE/configmaps

echo $URL
echo $YAML_FILE

echo "-------------------------------"

curl -H "Content-Type: application/yaml" -X POST $URL -d "$(cat $YAML_FILE)" -o ./log.txt -#
