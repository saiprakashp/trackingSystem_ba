#!/bin/bash
export TZ=GMT


 sudo -u tomcat /applications/tomcat/bin/stop_pts.sh
 sudo -u tomcat /applications/tomcat/bin/pts_start.sh
