package com.redhat.samples.switchyard.versioning.consumer;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.test.Invoker;
import org.switchyard.test.MockHandler;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;
import org.switchyard.test.SwitchYardTestKit;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML, mixins = CDIMixIn.class)
public class GreetingConsumerRouteTest {

    private SwitchYardTestKit testKit;

    @ServiceOperation("GreetingConsumerRoute")
    private Invoker consumer;

    @Before
    public void setUp() {
        testKit.removeService("GreetingServiceV10");
        testKit.removeService("GreetingServiceV11");
        MockHandler serviceV1_0 = testKit.registerInOutService("GreetingServiceV10");
        serviceV1_0.replyWithOut("V1.0");
        MockHandler serviceV1_1 = testKit.registerInOutService("GreetingServiceV11");
        serviceV1_1.replyWithOut("V1.1");
    }

    @Test
    public void route_v1_0() throws Exception {
        String request = readXML("/xml/hello-request_v1_0.xml");
        String response = consumer.sendInOut(request).getContent(String.class);
        assertThat(response, is("V1.0"));
    }

    @Test
    public void route_v1_1() throws Exception {
        String request = readXML("/xml/hello-request_v1_1.xml");
        String response = consumer.sendInOut(request).getContent(String.class);
        assertThat(response, is("V1.1"));
    }

    private String readXML(String path) throws IOException {
        return IOUtils.toString(getClass().getResource(path));
    }

}
