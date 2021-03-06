package com.redhat.samples.switchyard.versioning.service.v1_0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.component.bean.Service;

@Service(value = GreetingServicePortType.class, name = "GreetingService")
public class GreetingServiceImpl implements GreetingServicePortType {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceImpl.class);

    @Override
    public HelloResponse hello(Hello hello) {
        String message = String.format("Hello, %s!", hello.getName());
        LOGGER.info(message);
        HelloResponse response = new HelloResponse();
        response.setMessage(message);
        return response;
    }

}
