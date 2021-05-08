#!/bin/sh

# Renew letsencrypt certificates
sudo certbot renew

echo "Remove old keystore"
sudo rm /tmp/productivity.to.p12
sudo rm /tmp/productivity.to.keystore
sudo rm ~/ssl/productivity.to.keystore

echo "Create keystore"
keytool -genkey -alias defaultcert \
    -keyalg RSA -keystore /tmp/productivity.to.p12 \
    -dname "CN=Nykon, OU=Productivity, O=Productivity, L=Munich, S=Germany, C=DE" \
    -storepass PASSWORD -keypass PASSWORD

echo "Updating '~/ssl/productivity.to.keystore'"
openssl pkcs12 -export \
	 -in /etc/letsencrypt/live/productivity.to/cert.pem \
	 -inkey /etc/letsencrypt/live/productivity.to/privkey.pem \
	 -out /tmp/productivity.to.p12 \
	 -name productivity.to \
	 -CAfile /etc/letsencrypt/live/productivity.to/fullchain.pem \
	 -caname "Let's Encrypt Authority X3" \
	 -password pass:PASSWORD

echo "Importing to keystore"
keytool -importkeystore \
	-deststorepass PASSWORD \
	-destkeypass PASSWORD \
	-deststoretype pkcs12 \
	-srckeystore /tmp/productivity.to.p12 \
	-srcstoretype PKCS12 \
	-srcstorepass PASSWORD \
	-destkeystore /tmp/productivity.to.keystore \

echo "Copy '~/ssl/productivity.to.keystore' to ssl directory"
cp /tmp/productivity.to.keystore ~/ssl/productivity.to.keystore

echo "Restarting application"
sh restart-productivity.sh