# BPEL Service Sample

A basic BPEL service sample.

## How to run this example

1. Run SY server and target web service endpoint:

        $ cd ws/
        $ mvn clean install
        $ mvn exec:java

2. Open another terminal and deploy SY application:

        $ cd sy/
        $ mvn clean package jboss-as:deploy -DskipTests

3. Run client:

        $ mvn exec:java

