API_SERVER_HOST=$1
API_SERVER_PORT=$2
K8S_NAMESPACE=$3
K8S_TYPE=$4
YAML_FILE=$5
#Hard Code K8S API Version Temporary
API_VERSION=v1

#NAMESPACE POD SERVICE REPLICATIONCONTROLLER CONFIGMAP
if [ $K8S_TYPE == 'namespaces' ]
then
	URL=http://$API_SERVER_HOST:$API_SERVER_PORT/api/$API_VERSION/namespaces
else
	URL=http://$API_SERVER_HOST:$API_SERVER_PORT/api/$API_VERSION/namespaces/$K8S_NAMESPACE/$K8S_TYPE
fi

echo $URL
echo $YAML_FILE

echo "-------------------------------"

#curl -H "Content-Type: application/yaml" -X POST $URL -d "$(cat $YAML_FILE)" -o ./log.txt -#
curl -H "Content-Type: application/yaml" -X POST $URL -d "$(cat $YAML_FILE)"
