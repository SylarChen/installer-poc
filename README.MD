#How to run this demo:

Preparing base infra (single VM with k8s master and node)

Copy installer-poc.zip to your VM, unzip it, and enter “Docker” folder.

Build POC Image: “docker build -t suite-installer .”

Edit suiteconfig.yaml and suiteinstaller.yaml, change “SUITE_INSTALLER_IP” and “API_SERVER_IP” to your VM IP.

Run shell createFolderInPV.sh

Run command “kubectl --namespace=kube-system create -f suiteinstaller.yaml”

Check http://{ip}:30000/installer.html\
