package com.redhat.samples.switchyard;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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

import com.redhat.samples.switchyard.ws.PingServiceImpl;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML, mixins = { CDIMixIn.class })
public class PingServiceTest {

    private static Endpoint endpoint;

    @ServiceOperation("PingService")
    private Invoker service;

    @BeforeClass
    public static void startWebService() {
        try {
            endpoint = Endpoint.publish(PingServiceImpl.ENDPOINT_URL, new PingServiceImpl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void stopWebService() {
        if (endpoint != null) endpoint.stop();
    }

    @Test
    public void ping() {
        String result = service.operation("ping").sendInOut(null).getContent(String.class);
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void ping_beanClient() {
        String result = service.operation("ping").property("bean", true).sendInOut(null).getContent(String.class);
        assertThat(result, is(notNullValue()));
    }

}
