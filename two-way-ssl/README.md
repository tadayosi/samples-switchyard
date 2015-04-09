# 2-Way SSL Sample

This sample demonstrates how a SY application can talk with an external web service endpoint using [2-way SSL](http://en.wikipedia.org/wiki/Mutual_authentication).

## How to run this example

1. Launch a standalone web service endpoint:

        $ cd ws/
        $ mvn clean package
        $ mvn exec:java

2. Run SY server.

3. Copy client keystore to /tmp and deploy this SY application:

        $ cd sy/
        $ cp client.jks /tmp/
        $ mvn clean package jboss-as:deploy

