package com.redhat.samples.switchyard.versioning.service.v1_0;

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

    @Test
    public void hello() {
        Hello request = new Hello();
        request.setName("Test");
        HelloResponse result = hello.sendInOut(request).getContent(HelloResponse.class);
        assertThat(result.getMessage(), is(equalTo("Hello, Test!")));
    }

}
