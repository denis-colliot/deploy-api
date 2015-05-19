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
TOMCAT_WAR_NAME=api


# Override 'echo()' function.

function echo() { builtin echo '['$(basename $0 .sh)']' $1; }


# Script execution.

echo "Deployment of build #$BUILD_NUMBER with following tomcat artifact: $ARTIFACT_URL"

if [ -z $ARTIFACT_URL ];
then
    echo "Invalid artifact URL argument"
    exit 1;
fi

ARTIFACT_NAME=$(basename $ARTIFACT_URL)
ARTIFACT_LOG=$ARTIFACT_NAME".log"
ARTIFACT_EXTENSION="${ARTIFACT_NAME##*.}"

echo "Creating work directories (if they don't exist)"
mkdir -p $LOGS_BACKUP_FOLDER

echo "Downloading artifact '$ARTIFACT_NAME' into '$WORK_FOLDER'"
wget -N -P $WORK_FOLDER $ARTIFACT_URL
echo "Build #$BUILD_NUMBER" > $WORK_FOLDER/$ARTIFACT_LOG

echo "Stopping tomcat service (with 'nohup')"
nohup service $TOMCAT_SERVICE stop > "$0_nohup.out"

echo "Archiving tomcat logs"
tar -zcf $LOGS_BACKUP_FOLDER/tomcat-logs-$(date +%Y-%m-%d_%H-%M-%S).tar.gz -C $TOMCAT_HOME/logs .
rm -rf $TOMCAT_HOME/logs/*

echo "Removing existing '$TOMCAT_WAR_NAME' webapp (if any)"
rm -rf $TOMCAT_HOME/webapps/$TOMCAT_WAR_NAME*

echo "Creating symbolic link pointing to downloaded artifact"
ln -s $WORK_FOLDER/$ARTIFACT_NAME $TOMCAT_HOME/webapps/$TOMCAT_WAR_NAME.$ARTIFACT_EXTENSION

echo "Removing previous artifact with extension '.$ARTIFACT_EXTENSION' (except '$ARTIFACT_NAME')"
find $WORK_FOLDER -maxdepth 1 ! -name "$ARTIFACT_NAME" ! -name "$ARTIFACT_LOG" \( -name "*.$ARTIFACT_EXTENSION" -o -name "*.$ARTIFACT_EXTENSION.log" \) -exec rm -f {} +

echo "Starting tomcat service (with 'nohup')"
nohup service $TOMCAT_SERVICE start > "$0_nohup.out"

echo "Build #$BUILD_NUMBER deployment complete"