# springSecurity
Customized Spring security authentication and authorization with identity from header or from certificate

Steps to generate key store and trust store

##### create-keystore:
##### Generate a certificate authority (CA)
keytool -genkey -alias ca -ext san=dns:localhost,ip:127.0.0.1 -ext BC=ca:true -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass changeit -validity 3650 -dname "CN=cid CA,OU=myapp.com,O=myapp,L=SomeCity,ST=SomeState,C=CC" -keystore keystore.jks -storepass changeit

##### add-host:
##### Generate a host certificate
keytool -genkey -alias localhost -ext san=dns:localhost,ip:127.0.0.1  -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass changeit -validity 3650 -dname "CN=cid,OU=myapp.com,O=myapp,L=SomeCity,ST=SomeState,C=CC" -keystore keystore.jks -storepass changeit

##### Generate a host certificate signing request
keytool -certreq -alias localhost -ext san=dns:localhost,ip:127.0.0.1 -ext BC=ca:true -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -validity 3650 -file "localhost.csr" -keystore keystore.jks -storepass changeit

##### Generate signed certificate with the certificate authority
keytool -gencert -alias ca -ext san=dns:localhost,ip:127.0.0.1 -validity 3650 -sigalg SHA512withRSA -infile "localhost.csr" -outfile "localhost.crt" -rfc -keystore keystore.jks -storepass changeit

##### Import signed certificate into the keystore
keytool -import -trustcacerts -alias localhost -ext san=dns:localhost,ip:127.0.0.1 -file "localhost.crt" -keystore keystore.jks -storepass changeit

##### export-authority:
##### Export certificate authority
keytool -export -alias ca -ext san=dns:localhost,ip:127.0.0.1 -file ca.crt -rfc -keystore keystore.jks -storepass changeit

##### create-truststore: export-authority
##### Import certificate authority into a new truststore
keytool -import -trustcacerts -noprompt -alias ca -ext san=dns:localhost,ip:127.0.0.1 -file ca.crt -keystore truststore.jks -storepass changeit

##### add-client:
##### Generate client certificate
keytool -genkey -alias cid_pk -ext san=dns:localhost,ip:127.0.0.1 -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -keypass changeit -validity 3650 -dname "CN=cid,OU=myapp.com,O=myapp,L=SomeCity,ST=SomeState,C=CC" -keystore truststore.jks -storepass changeit

##### Generate a host certificate signing request
keytool -certreq -alias cid_pk -ext san=dns:localhost,ip:127.0.0.1 -ext  BC=ca:true -keyalg RSA -keysize 4096 -sigalg SHA512withRSA -validity 3650 -file "cid.csr" -keystore truststore.jks -storepass changeit
##### Generate signed certificate with the certificate authority
keytool -gencert -alias ca -ext san=dns:localhost,ip:127.0.0.1 -validity 3650 -sigalg SHA512withRSA -infile "cid.csr" -outfile "cid.crt" -rfc -keystore keystore.jks -storepass changeit
##### Import signed certificate into the truststore
keytool -import -trustcacerts -alias cid -ext san=dns:localhost,ip:127.0.0.1 -file "cid.crt" -keystore truststore.jks -storepass changeit
##### Export private certificate for importing into a browser
keytool -importkeystore -srcalias cid_pk -ext san=dns:localhost,ip:127.0.0.1 -srckeystore truststore.jks -srcstorepass changeit -destkeystore "cid.p12" -deststorepass changeit -deststoretype PKCS12
##### Delete client private key as truststore should not contain any private keys
keytool -delete -alias cid_pk -keystore truststore.jks -storepass changeit


