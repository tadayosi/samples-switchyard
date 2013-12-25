# HTTP Proxy Sample

This sample demonstrates how to implement HTTP proxy application using HTTP / Camel URI binding and Camel / Bean services in SY.

## Prerequisites

Before running this example, you need to install the `camel-http` component into SY. For how to install a Camel component into SY, see: https://docs.jboss.org/author/display/SWITCHYARD/Extensions

## How to run this example

1. Run target REST service:

        $ cd rest/
        $ mvn clean package jetty:run

2. Run SY server and deploy HTTP proxy SY application:

        $ cd sy/
        $ mvn clean package jboss-as:deploy

3. Run HTTP client:

        $ cd sy/
        $ mvn exec:java

