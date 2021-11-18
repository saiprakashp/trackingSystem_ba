#!/bin/bash
location=/applications/db_dump/PTS_DB_`date +%Y%m%d_%H%M%S`.sql
rm -rf /applications/db_dump/*
mysqldump  -u root --password=root --databases PTS > /applications/db_dump/PTS_DB_BKP.sql
gzip /applications/db_dump/PTS_DB_BKP.sql
