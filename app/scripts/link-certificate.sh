#!/bin/bash
# With create-react-app, a self signed (therefore invalid) certificate is generated.
# 1. Create some folder in the root of your project
# 2. Copy your valid development certificate to this folder
# 3. Copy this file to the same folder
# 4. In you package.json, under `scripts`, add `postinstall` script that runs this file.
# Every time a user runs npm install this script will make sure to copy the certificate to the
# correct location

TARGET_LOCATION_CERT="./node_modules/webpack-dev-server/ssl/server.pem"
SOURCE_LOCATION_CERT=$(pwd)/$(dirname "./certificate/server.pem")/server.pem

#TARGET_LOCATION_KEY="./node_modules/webpack-dev-server/ssl/eventacskey.pem"
#SOURCE_LOCATION_KEY=$(pwd)/$(dirname "./certificate/eventacskey.pem")/eventacskey.pem

echo Linking ${TARGET_LOCATION_CERT} TO ${SOURCE_LOCATION_CERT}
rm -f ${TARGET_LOCATION_CERT} || true
ln -s ${SOURCE_LOCATION_CERT} ${TARGET_LOCATION_CERT}
chmod 400 ${TARGET_LOCATION_CERT} # after 30 days create-react-app tries to generate a new certificate and overwrites the existing one.
echo "Created server.pem symlink"

#echo Linking ${TARGET_LOCATION_KEY} TO ${SOURCE_LOCATION_KEY}
#rm -f ${TARGET_LOCATION_KEY} || true
#ln -s ${SOURCE_LOCATION_KEY} ${TARGET_LOCATION_KEY}
#chmod 400 ${TARGET_LOCATION_KEY} # after 30 days create-react-app tries to generate a new certificate and overwrites the existing one.
#echo "Created eventacskey.pem symlink"