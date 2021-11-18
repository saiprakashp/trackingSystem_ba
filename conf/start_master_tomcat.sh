#!/bin/bash
export TZ=GMT

 echo "Starting Master Tomcat"
  sudo -u tomcat /applications/softwares/apache-tomcat-9.0.46/bin/catalina.sh stop
 sudo -u tomcat /applications/softwares/apache-tomcat-9.0.46/bin/catalina.sh start
