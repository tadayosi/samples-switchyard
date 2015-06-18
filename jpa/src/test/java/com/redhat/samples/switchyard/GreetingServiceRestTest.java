package com.redhat.samples.switchyard;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.resteasy.resource.ResourcePublisher;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.component.test.mixins.http.HTTPMixIn;
import org.switchyard.component.test.mixins.transaction.TransactionMixIn;
import org.switchyard.test.BeforeDeploy;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(
        mixins = { CDIMixIn.class, HTTPMixIn.class, TransactionMixIn.class },
        config = SwitchYardTestCaseConfig.SWITCHYARD_XML)
public class GreetingServiceRestTest extends GreetingServiceTestBase {

    private static String BASE_URL = "http://localhost:18080/sample";

    private HTTPMixIn httpMixIn;

    @BeforeDeploy
    public void setProperties() {
        System.setProperty(ResourcePublisher.DEFAULT_PORT_PROPERTY, "18080");
    }

    @Test
    public void hello_goodbye() {
        String response1 = httpMixIn.sendString(BASE_URL + "/hello/Test", null, HTTPMixIn.HTTP_GET);
        assertThat(response1, is("{\"result\" : \"Hello, Test!\"}"));

        String response2 = httpMixIn.sendString(BASE_URL + "/goodbye/Test", null, HTTPMixIn.HTTP_GET);
        assertThat(response2, is("<goodbye><result>Goodbye, Test!</result></goodbye>"));
    }

    @Ignore("TODO: two test methods somehow don't work")
    @Test
    public void goodbye() {
        String response = httpMixIn.sendString(BASE_URL + "/goodbye/Test", null, HTTPMixIn.HTTP_GET);
        assertThat(response, is("<goodbye><result>Goodbye, Test!</result></goodbye>"));
    }

}
