package com.redhat.samples.switchyard;

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
        String result = hello.sendInOut("Test").getContent(String.class);
        assertThat(result, is("Hello, Test!"));
    }

    @Test
    public void goodbye() {
        String result = goodbye.sendInOut("Test").getContent(String.class);
        assertThat(result, is("Goodbye, Test!"));
    }

}
