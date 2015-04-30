#!/bin/sh

DEPLOY_REP="/home/jenkins/observatoire"
WWW_REP="/var/www/observatoire"

echo 'Starting deployment process.'

if [ -f $DEPLOY_REP/*.zip ];
then
        echo "Front archive (zip) present in deploy repertory '$DEPLOY_REP'."

        echo "Stopping Apache service."
        service httpd stop

        echo "Replacing content of repertory '$WWW_REP'."
        rm -rf $WWW_REP/*
        find $WWW_REP -type f -name '.*' | xargs rm
        unzip $DEPLOY_REP/*.zip -d $WWW_REP

        echo "Starting Apache service."
        service httpd start
fi

echo "Deleting deployed archive from repertory '$DEPLOY_REP'."
rm -f $DEPLOY_REP/*.zip

echo "Deployment process complete."