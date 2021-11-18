#!/bin/bash
now=$(date +"%m_%d_%Y")
tar -zcvf logs_$now.tar /applications/tomcat/logs/*
tar -zcvf logs_http_$now.tar /etc/httpd/logs/*
rm -rf /applications/tomcat/logs/catalina.20*
rm -rf /applications/tomcat/logs/host-manager.20*
rm -rf /applications/tomcat/logs/localhost.20*
rm -rf /applications/tomcat/logs/manager.20*
rm -rf /applications/tomcat/logs/pts.log.*
rm -rf /applications/tomcat/logs/localhost_access_log.20*
mv logs_*.tar /dev/logbkp/*
rm -rf logs_*.tar
rm -rf /applications/tomcat/logs/access_log-20* 
rm -rf /applications/tomcat/logs/error_log-20*
rm -rf /applications/tomcat/logs/proxyssl_access_log-20*
rm -rf /applications/tomcat/logs/ssl_access_log-20*
rm -rf /applications/tomcat/logs/ssl_request_log-20*
