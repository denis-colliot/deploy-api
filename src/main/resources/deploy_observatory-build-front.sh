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
APACHE_WWW_REP=/var/www/observatoire
APACHE_SERVICE="httpd"


# Override 'echo()' function.

function echo() { builtin echo '['$(basename $0 .sh)']' $1; }


# Script execution.

echo "Deployment of build #$BUILD_NUMBER with following apache artifact: $ARTIFACT_URL"

if [ -z $ARTIFACT_URL ];
then
    echo "Invalid artifact URL argument"
    exit 1;
fi

ARTIFACT_NAME=$(basename $ARTIFACT_URL)
ARTIFACT_LOG=$ARTIFACT_NAME".log"
ARTIFACT_EXTENSION="${ARTIFACT_NAME##*.}"

echo "Creating work directories (if they don't exist)"
mkdir -p $WORK_FOLDER

echo "Downloading artifact '$ARTIFACT_NAME' into '$WORK_FOLDER'"
wget -N -P $WORK_FOLDER $ARTIFACT_URL
echo "Build #$BUILD_NUMBER" > $WORK_FOLDER/$ARTIFACT_LOG

echo "Stopping Apache service (with 'nohup')"
nohup service $APACHE_SERVICE stop > "$0_nohup.out"

echo "Replacing content of repertory '$APACHE_WWW_REP'"
rm -rf $APACHE_WWW_REP/*
find $APACHE_WWW_REP -type f -name '.*' | xargs rm
# TODO handle other extensions than 'zip'.
unzip $WORK_FOLDER/$ARTIFACT_NAME -d $APACHE_WWW_REP

echo "Starting Apache service (with 'nohup')"
nohup service $APACHE_SERVICE start > "$0_nohup.out"

echo "Deleting previous downloaded archives from directory '$WORK_FOLDER'"
find $WORK_FOLDER -maxdepth 1 ! -name "$ARTIFACT_NAME" ! -name "$ARTIFACT_LOG" \( -name "*.$ARTIFACT_EXTENSION" -o -name "*.$ARTIFACT_EXTENSION.log" \) -exec rm -f {} +

echo "Build #$BUILD_NUMBER deployment complete"