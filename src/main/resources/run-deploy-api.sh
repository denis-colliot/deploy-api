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

# Deploy API (Java ARchive) to execute.
JAR_FILE="deploy-api-0.0.1-SNAPSHOT.jar"

# Deploy API configuration file.
CONF_FILE="deploy-api-conf"

# Deploy API directory.
DEPLOY_DIR=/var/local/deploy-api


# Override 'echo()' function.

function echo() { builtin echo '['$(basename $0 .sh)']' $1; }


# Script execution.

cd $DEPLOY_DIR


# Displaying arguments.
echo "Environment: '$1'."
echo "Build: '$2'."
echo "Build number: '$3'."


# Executing JAR.
export SPRING_CONFIG_NAME=$CONF_FILE
java -jar $JAR_FILE $1 $2 $3

exitValue=$?

if [ $exitValue != 0 ]
then
    exit $exitValue
fi

echo "DONE"