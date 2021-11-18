#!/bin/sh
DATE=$(date +%d-%m-%Y)
BACKUP_DIR="/applications/dbbackups"
MYSQL_USER="ptsusr"
MYSQL_PASSWORD="ptspass"
DB_NAME="PTS"

umask 177
# Dump database into SQL file
mysqldump --user=$MYSQL_USER --password=$MYSQL_PASSWORD $DB_NAME > $BACKUP_DIR/$DB_NAME-$DATE.sql
chmod -R 755 $BACKUP_DIR/$DB_NAME-$DATE.sql