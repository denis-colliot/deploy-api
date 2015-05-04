#!/bin/sh

# ----------------------------------------------------------------------------
# This script deploys a given tomcat artifact.
# Archives logs and restarts service.
#
# @author: Denis Colliot (denis.colliot@zenika.com)
#
# Script arguments:
#   $1: build number.
#   $2: artifact download URL.
# ----------------------------------------------------------------------------

echo "Starting script '$0' execution"

# System properties.

BUILD_NUMBER=$1
ARTIFACT_URL=$2
WORK_FOLDER=/var/local/observatoire
LOGS_BACKUP_FOLDER=$WORK_FOLDER/logs-backups
TOMCAT_HOME=/usr/local/tomcat8
TOMCAT_SERVICE=tomcat8


# Script execution.

echo "Deployment of build #$BUILD_NUMBER with following tomcat artifact: $ARTIFACT_URL"

if [ -z $ARTIFACT_URL ];
then
    echo "Invalid artifact URL argument"
    exit 1;
fi

FILE_NAME=$(basename $ARTIFACT_URL)
FILE_EXTENSION="${FILE_NAME##*.}"

echo "Creating work directories (if they don't exist)"
mkdir -p $LOGS_BACKUP_FOLDER

echo "Downloading artifact '$FILE_NAME' into '$WORK_FOLDER'"
wget --directory-prefix=WORK_FOLDER $ARTIFACT_URL

echo "Stopping tomcat service"
service $TOMCAT_SERVICE stop

echo "Archiving tomcat logs"
tar -zcf $LOGS_BACKUP_FOLDER/tomcat-logs-$(date +%Y-%m-%d_%H-%M-%S).tar.gz -C $TOMCAT_HOME/logs .
rm -rf $TOMCAT_HOME/logs/*

echo "Removing existing ROOT webapp (if any)"
rm -rf $TOMCAT_HOME/webapps/ROOT*

echo "Creating symbolic link pointing to downloaded artifact"
ln -s $WORK_FOLDER/$FILE_NAME $TOMCAT_HOME/webapps/ROOT.$FILE_EXTENSION

echo "Removing previous artifact with extension '.$FILE_EXTENSION'"
find $WORK_FOLDER ! -name '$FILE_NAME' -regex ".*\.$FILE_EXTENSION" -exec rm -f {} +

echo "Starting tomcat service"
service $TOMCAT_SERVICE start

echo "Build #$BUILD_NUMBER deployment complete."