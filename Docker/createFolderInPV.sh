mkdir /var/vols/ITOM/suite-install
mkdir /var/vols/ITOM/suite-install/itsma
mkdir /var/vols/ITOM/suite-install/itsma/output

cp suiteconfig_svc.yaml /var/vols/ITOM/suite-install/itsma
cp suiteconfig.yaml /var/vols/ITOM/suite-install/itsma

kubectl create namespace itsma
