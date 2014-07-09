# SwitchYard Cross-Origin Resource Sharing (CORS) Sample

This sample demonstrates how to enable CORS for RESTEasy service binding in SwitchYard.

For more information, see also: https://community.jboss.org/thread/240807

## How to run this example

1. Start JBoss AS 7

2. Deploy the SwitchYard application:

        $ cd sy/
        $ mvn clean package jboss-as:deploy

3. Run the other web server:

        $ cd js/
        $ mvn jetty:run

4. Access http://localhost:18080/
