# SwitchYard Service Versioning Sample

This sample demonstrates how to implement and utilise service versioning in SY.

## How to run this example

1. Run SY server
2. Deploy versioned SY services:

        $ cd service-1.0/
        $ mvn clean package jboss-as:deploy
        $ cd service-1.1/
        $ mvn clean package jboss-as:deploy

3. Deploy SY consumer:

        $ cd consumer/
        $ mvn clean package jboss-as:deploy

4. Run consumer client:

        $ cd consumer/
        $ mvn exec:java
