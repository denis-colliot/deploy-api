#!/bin/sh

# ----------------------------------------------------------------------------
# Executes the executable Java ARchive 'deploy-api.jar'.
#
# @author: Denis Colliot (denis.colliot@zenika.com)
#
# Script arguments:
#   $1: environment.
#   $2: build name.
#   $3: (optional) build number.
# ----------------------------------------------------------------------------

JAR_FILE="deploy-api-0.0.1-SNAPSHOT.jar"
CONF_FILE="deploy-api-conf"

cd /var/local/deploy-api/


# Displaying arguments.
echo "> Environment: '$1'."
echo "> Build: '$2'."
echo "> Build number: '$3'."
echo "> Java ARchive (JAR) file: '$ARCHIVE_FILE'."


# Executing JAR.
export SPRING_CONFIG_NAME=$CONF_FILE
java -jar $JAR_FILE $1 $2 $3

echo "> DONE"