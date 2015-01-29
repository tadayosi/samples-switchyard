package com.redhat.samples.switchyard;

import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;
import org.switchyard.test.SwitchYardTestKit;

import com.redhat.samples.switchyard.ws.GreetingService;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML, mixins = CDIMixIn.class)
public class GreetingServiceTest {

    private static final String WS_ENDPOINT = "http://localhost:18080/greeting";

    private static Endpoint endpoint;

    private SwitchYardTestKit testKit;

    @ServiceOperation("GreetingService")
    private Invoker service;

    @BeforeClass
    public static void startWS() {
        endpoint = Endpoint.create(new GreetingService());
        endpoint.publish(WS_ENDPOINT);
    }

    @AfterClass
    public static void stopWS() {
        endpoint.stop();
    }

    @Test
    public void hello() throws Exception {
        String request = testKit.readResourceString("xml/hello-request.xml");
        String response = service.operation("hello").sendInOut(request).getContent(String.class);
        testKit.compareXMLToResource(response, "xml/hello-response.xml");
    }

    @Test
    public void goodbye() throws Exception {
        String request = testKit.readResourceString("xml/goodbye-request.xml");
        String response = service.operation("goodbye").sendInOut(request).getContent(String.class);
        testKit.compareXMLToResource(response, "xml/goodbye-response.xml");
    }

}
