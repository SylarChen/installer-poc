FROM java:8
#ADD suiteconfig.yaml /
#ADD suiteconfig_svc.yaml /
#

RUN export http_proxy=http://web-proxy.atl.hp.com:8080 && export https_proxy=http://web-proxy.atl.hp.com:8080 && \
apt-get update && apt-get upgrade -y && \
apt-get install -y vim

ADD createpod.sh /
RUN chmod +x /createpod.sh
#RUN mkdir /suite_conf_yamls
#RUN mkdir /suite_conf_yamls/itsma/
#ADD suiteconfig.yaml /suite_conf_yamls/itsma/
#ADD suiteconfig_svc.yaml /suite_conf_yamls/itsma/
#ADD suiteconfig_cm.yaml /suite_conf_yamls/itsma/

#ADD createsinglepod.sh /
#RUN chmod +x /createsinglepod.sh
#ADD createsingleconfigmap.sh /
#RUN chmod +x /createsingleconfigmap.sh
#ADD createsingleservice.sh /
#RUN chmod +x /createsingleservice.sh

#RUN mkdir /bin
ADD bin/kubectl_create.sh /bin
RUN chmod +x /bin/kubectl_create.sh



#Suite installer mountpath
RUN mkdir /pv_suite_install
#Suite configuration mountpath
#RUN mkdir /pv_suite_conf

#Suite configuration folder holds the yamls
#ADD suite_yamls /suite_yamls
#ADD copyYamls.sh /
#RUN chmod +x /copyYamls.sh
#ADD postBody.json /
#ADD askCreateBusinessPod.sh /
#RUN chmod +x /askCreateBusinessPod.sh

ADD suite-installer-1.0.jar /
ADD entrypoint.sh /
ENTRYPOINT ["/bin/bash","/entrypoint.sh"]
