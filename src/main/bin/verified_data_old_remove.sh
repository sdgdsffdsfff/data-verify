#!/bin/bash

MYSQL=`which mysql`
$MYSQL -h 192.168.32.20 crawler -ucrawler -pzxsoft <<EOF 
   DELETE FROM verified_data WHERE lasttime < NOW() - INTERVAL 1 DAY;
EOF


