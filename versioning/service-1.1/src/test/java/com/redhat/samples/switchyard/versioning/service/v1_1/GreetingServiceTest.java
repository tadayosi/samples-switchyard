package com.redhat.samples.switchyard.versioning.service.v1_1;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(mixins = CDIMixIn.class)
public class GreetingServiceTest {

    @ServiceOperation("GreetingService.hello")
    private Invoker hello;

    @ServiceOperation("GreetingService.goodbye")
    private Invoker goodbye;

    @Test
    public void hello() {
        Hello request = new Hello();
        request.setName("Test");
        HelloResponse result = hello.sendInOut(request).getContent(HelloResponse.class);
        assertThat(result.getMessage(), is(equalTo("Hello, Test!")));
    }

    @Test
    public void goodbye() {
        Goodbye request = new Goodbye();
        request.setName("Test");
        GoodbyeResponse result = goodbye.sendInOut(request).getContent(GoodbyeResponse.class);
        assertThat(result.getMessage(), is(equalTo("Goodbye, Test!")));
    }

}
