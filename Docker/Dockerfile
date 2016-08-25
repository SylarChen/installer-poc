FROM java:8
ADD suite-installer-1.0.jar /

#ADD suiteconfig.yaml /
#ADD suiteconfig_svc.yaml /
ADD createpod.sh /
RUN chmod +x /createpod.sh
ADD createsinglepod.sh /
RUN chmod +x /createsinglepod.sh

#Suite installer mountpath
RUN mkdir /pv_suite_install
#Suite configuration mountpath
RUN mkdir /pv_suite_conf

#Suite configuration folder holds the yamls
ADD suite_yamls /suite_yamls
ADD copyYamls.sh /
RUN chmod +x /copyYamls.sh

ADD entrypoint.sh /
ENTRYPOINT ["/bin/bash","/entrypoint.sh"]
