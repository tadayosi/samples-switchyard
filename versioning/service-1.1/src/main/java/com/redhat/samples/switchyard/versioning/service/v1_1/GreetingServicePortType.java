package com.redhat.samples.switchyard.versioning.service.v1_1;


public interface GreetingServicePortType {

    public HelloResponse hello(
        com.redhat.samples.switchyard.versioning.service.v1_1.Hello parameters);

    public GoodbyeResponse goodbye(
        com.redhat.samples.switchyard.versioning.service.v1_1.Goodbye parameters);
}
