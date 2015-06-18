package com.redhat.samples.switchyard;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.Service;

@Service(GreetingService.class)
public class GreetingServiceBean implements GreetingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceBean.class);

    @Inject
    @Reference
    private GreetingHistory history;

    @Override
    public String hello(String name) {
        String message = String.format("Hello, %s!", name);

        Greeting greeting = new Greeting(message, new Date());
        history.store(greeting);
        LOGGER.info(greeting.toString());

        return String.format("{\"result\" : \"%s\"}", message);
    }

    @Override
    public String goodbye(String name) {
        String message = String.format("Goodbye, %s!", name);

        Greeting greeting = new Greeting(message, new Date());
        history.store(greeting);
        LOGGER.info(greeting.toString());

        return String.format("<goodbye><result>%s</result></goodbye>", message);
    }

}
