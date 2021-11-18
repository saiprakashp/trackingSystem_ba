#!/bin/bash
export TZ=GMT

echo "Starting PTS db"
systemctl start mariadb
	

 echo "Starting PTS Tomcat"
 sudo -u tomcat /applications/tomcat/bin/stop_pts.sh
 sudo -u tomcat /applications/tomcat/bin/pts_start.sh
 
 echo "Starting Master Tomcat"
  sudo -u tomcat /applications/softwares/apache-tomcat-9.0.46/bin/catalina.sh stop
 sudo -u tomcat /applications/softwares/apache-tomcat-9.0.46/bin/catalina.sh start
  
  echo "Starting PTS Apache"
 /applications/apache/bin/apachectl -k stop
 /applications/apache/bin/apachectl -k start
