package com.redhat.samples.switchyard;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.component.test.mixins.transaction.TransactionMixIn;
import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(
        mixins = { CDIMixIn.class, TransactionMixIn.class },
        config = SwitchYardTestCaseConfig.SWITCHYARD_XML)
public class GreetingServiceTest extends GreetingServiceTestBase {

    @ServiceOperation("GreetingService.hello")
    private Invoker hello;

    @ServiceOperation("GreetingService.goodbye")
    private Invoker goodbye;

    @Test
    public void hello_goodbye() {
        String result1 = hello.sendInOut("Test").getContent(String.class);
        assertThat(result1, is("{\"result\" : \"Hello, Test!\"}"));

        String result2 = goodbye.sendInOut("Test").getContent(String.class);
        assertThat(result2, is("<goodbye><result>Goodbye, Test!</result></goodbye>"));
    }

    @Ignore("TODO: two test methods somehow don't work")
    @Test
    public void goodbye() {
        String result = goodbye.sendInOut("Test").getContent(String.class);
        assertThat(result, is("<goodbye><result>Goodbye, Test!</result></goodbye>"));
    }

}
