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
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML, mixins = { CDIMixIn.class })
public class GreetingServiceTest {

    @ServiceOperation("GreetingService")
    private Invoker service;

    @Test
    public void route() {
        String result = service.operation("").sendInOut("Test").getContent(String.class);
        assertThat(result, is("Hello, Test!"));
    }

}
