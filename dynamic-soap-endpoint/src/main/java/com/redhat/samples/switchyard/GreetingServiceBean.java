package com.redhat.samples.switchyard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.component.bean.Service;

import com.redhat.samples.switchyard.goodbye.Goodbye;
import com.redhat.samples.switchyard.goodbye.GoodbyeResponse;
import com.redhat.samples.switchyard.hello.Hello;
import com.redhat.samples.switchyard.hello.HelloResponse;

@Service(GreetingService.class)
public class GreetingServiceBean implements GreetingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceBean.class);

    @Override
    public HelloResponse hello(Hello hello) {
        LOGGER.info("[hello] name = {}", hello.getName());
        HelloResponse response = new HelloResponse();
        response.setMessage("Hello, " + hello.getName() + "!");
        return response;
    }

    @Override
    public GoodbyeResponse goodbye(Goodbye goodbye) {
        LOGGER.info("[goodbye] name = {}", goodbye.getName());
        GoodbyeResponse response = new GoodbyeResponse();
        response.setMessage("Goodbye, " + goodbye.getName() + "!");
        return response;
    }

}
