package com.redhat.samples.switchyard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.component.bean.Service;

@Service(GreetingService.class)
public class GreetingServiceImpl implements GreetingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceImpl.class);

    @Override
    public String hello(String name) {
        String message = String.format("Hello, %s!", name);
        LOGGER.info(message);
        return message;
    }

    @Override
    public String goodbye(String name) {
        String message = String.format("Goodbye, %s!", name);
        LOGGER.info(message);
        return message;
    }

}
