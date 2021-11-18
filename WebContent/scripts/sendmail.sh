#!/bin/sh

LogFile_Date_Format="+%d %h %Y %H:%M:%S"
MAIL_JAVACLIENT=com.egil.pts.util.GenericMail
PTS_LOGFILE=/applications/tomcat/logs/pts/pts.log

export CLASSPATH=/applications/tomcat/lib/mariadb-java-client-2.2.0.jar:/applications/tomcat/webapps/pts/WEB-INF/lib/log4j-1.2.17.jar:/applications/tomcat/webapps/pts/WEB-INF/lib/mail.jar:/applications/tomcat/webapps/pts/WEB-INF/lib/xercesImpl.jar:/applications/tomcat/lib/ojdbc7-12.1.0.1.jar:/applications/tomcat/webapps/pts/WEB-INF/lib/pts_ui.jar:/applications/tomcat/webapps/pts/WEB-INF/lib/pts_common.jar:/applications/tomcat/webapps/pts/WEB-INF/lib/pts_service.jar:/applications/tomcat/webapps/pts/WEB-INF/lib/pts_dao.jar

sendMail()
{
result=0
result=$(java -classpath $CLASSPATH -XX:+useParallelGC -Xms1024m -Xmx2048m -XX:Permsize=256m -XX:MaxPermsSize=256m com.egil.pts.util.GenericMail)
if [[ ${result} -ne 0]]
then
		echo $(date "$LogFile_Date_Format")  "unable to send mail to support team " >> $PTS_LOGFILE
fi
}

sendMail		
		 
