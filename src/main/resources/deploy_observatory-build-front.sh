#!/bin/sh

# ----------------------------------------------------------------------------
# This script deploys a given apache artifact.
# Restarts service.
#
# @author: Denis Colliot (denis.colliot@zenika.com)
#
# Script arguments:
#   $1: build number.
#   $2: artifact download URL (should be a 'zip' archive).
# ----------------------------------------------------------------------------

echo "Starting script '$0' execution"

# System properties.

BUILD_NUMBER=$1
ARTIFACT_URL=$2
WORK_FOLDER=/var/local/observatoire
APACHE_WWW_REP="/var/www/observatoire"
APACHE_SERVICE="httpd"


# Script execution.

echo "Deployment of build #$BUILD_NUMBER with following apache artifact: $ARTIFACT_URL"

if [ -z $ARTIFACT_URL ];
then
    echo "Invalid artifact URL argument"
    exit 1;
fi

FILE_NAME=$(basename $ARTIFACT_URL)
FILE_EXTENSION="${FILE_NAME##*.}"

echo "Creating work directories (if they don't exist)"
mkdir -p $WORK_FOLDER

echo "Downloading artifact '$FILE_NAME' into '$WORK_FOLDER'"
wget --directory-prefix=$WORK_FOLDER $ARTIFACT_URL

echo "Stopping Apache service."
service $APACHE_SERVICE stop

echo "Replacing content of repertory '$APACHE_WWW_REP'."
rm -rf $APACHE_WWW_REP/*
find $APACHE_WWW_REP -type f -name '.*' | xargs rm
# TODO handle other extensions than 'zip'.
unzip $WORK_FOLDER/$FILE_NAME -d $APACHE_WWW_REP

echo "Starting Apache service."
service $APACHE_SERVICE start

echo "Deleting previous downloaded archives from directory '$WORK_FOLDER'."
find $WORK_FOLDER ! -name '$FILE_NAME' -regex ".*\.$FILE_EXTENSION" -exec rm -f {} +

echo "Build #$BUILD_NUMBER deployment complete."