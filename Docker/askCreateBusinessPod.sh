echo "API_SERVER_IP: " $SUITE_INSTALLER_IP > /businessPod.log

echo "API_SERVER_PORT: " $SUITE_INSTALLER_PORT >> /businessPod.log

curl -H "Content-Type: application/json" -X POST http://$SUITE_INSTALLER_IP:$SUITE_INSTALLER_PORT/suiteinstaller/install -d "$(cat /postBody.json)" >> /businessPod.log

