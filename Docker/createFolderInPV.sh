mkdir /var/vols/ITOM/suite-install
mkdir /var/vols/ITOM/suite-install/itsma
mkdir /var/vols/ITOM/suite-install/itsma/output


cp suiteconfig_cm.yaml /var/vols/ITOM/suite-install/itsma
cp suiteconfig_ing.yaml /var/vols/ITOM/suite-install/itsma
cp suiteconfig_svc.yaml /var/vols/ITOM/suite-install/itsma
cp suiteconfig.yaml /var/vols/ITOM/suite-install/itsma

cp suite_yamls/am_configmap.yaml /var/vols/ITOM/suite-install/itsma/output/configmap.yaml
cp suite_yamls/configmap.yaml /var/vols/ITOM/suite-install/itsma/output/cm.yaml
cp suite_yamls/amsuite.yaml /var/vols/ITOM/suite-install/itsma/output/
#kubectl create namespace itsma
