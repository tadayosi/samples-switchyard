# REST Proxy Sample

This sample demonstrates how to implement REST proxy application using REST binding and Camel / Bean services in SY.

## How to run this example

1. Run target REST service:

        $ cd rest/
        $ mvn clean package jetty:run

2. Run SY server and deploy REST proxy SY application:

        $ cd sy/
        $ mvn clean package jboss-as:deploy

3. Run REST client:

        $ cd sy/
        $ mvn exec:java
