# HTTP Redirection Sample

This sample demonstrates how a SY application can handle HTTP redirection (`3xx`) from a web service endpoint.

## How to run this example

1. Run SY server and deploy target web service endpoint:

        $ cd ws/
        $ mvn clean package jboss-as:deploy

2. Deploy HTTP redirection SY application:

        $ cd sy/
        $ mvn clean package jboss-as:deploy

