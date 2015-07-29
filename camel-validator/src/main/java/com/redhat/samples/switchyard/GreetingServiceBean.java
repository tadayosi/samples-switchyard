package com.redhat.samples.switchyard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.component.bean.Service;

@Service(value = GreetingService.class, name = "GreetingServiceBean")
public class GreetingServiceBean implements GreetingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceBean.class);

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
