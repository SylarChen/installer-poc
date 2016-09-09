#!/bin/bash
echo "Enter shell" >> /kubectl.log
API_SERVER_HOST=$1
API_SERVER_PORT=$2
K8S_NAMESPACE=$3
K8S_TYPE=$4
YAML_FILE=$5
#------------------------------------------------
#Hard Code K8S API Version Temporary
API_VERSION=v1
EXTENSION_API_VERSION=extensions/v1beta1

#Avariable K8S Types
NORMAL_TYPES=(configmaps services replicationcontrollers pods)
EXTENSION_TYPES=(ingresses deployments)

echo "Generate URL..."
if [ $K8S_TYPE == 'namespaces' ]; then
	URL=http://$API_SERVER_HOST:$API_SERVER_PORT/api/$API_VERSION/namespaces
elif echo "${NORMAL_TYPES[@]}" | grep -w "$K8S_TYPE" &>/dev/null; then	
	URL=http://$API_SERVER_HOST:$API_SERVER_PORT/api/$API_VERSION/namespaces/$K8S_NAMESPACE/$K8S_TYPE
elif echo "${EXTENSION_TYPES[@]}" | grep -w "$K8S_TYPE" &>/dev/null; then
	URL=http://$API_SERVER_HOST:$API_SERVER_PORT/apis/$EXTENSION_API_VERSION/namespaces/$K8S_NAMESPACE/$K8S_TYPE	
else
	echo "Type $K8S_TYPE does not supported." >> /kubectl.log
	exit -1;
fi

echo $URL >> /kubectl.log
echo $YAML_FILE >> /kubectl.log

echo "-------------------------------" >> /kubectl.log

#curl -H "Content-Type: application/yaml" -X POST $URL -d "$(cat $YAML_FILE)" -o ./log.txt -#
curl -H "Content-Type: application/yaml" -X POST $URL -d "$(cat $YAML_FILE)" >> /kubectl.log
