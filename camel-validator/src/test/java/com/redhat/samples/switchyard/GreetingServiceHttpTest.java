package com.redhat.samples.switchyard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.http.endpoint.StandaloneEndpointPublisher;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.component.test.mixins.http.HTTPMixIn;
import org.switchyard.test.BeforeDeploy;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(
        config = SwitchYardTestCaseConfig.SWITCHYARD_XML,
        mixins = { CDIMixIn.class, HTTPMixIn.class })
public class GreetingServiceHttpTest {

    private static final String ENDPOINT_URL = "http://localhost:18080/sample/greeting";

    private HTTPMixIn httpMixIn;

    @BeforeDeploy
    public void setProperties() {
        System.setProperty(StandaloneEndpointPublisher.DEFAULT_PORT_PROPERTY, "18080");
    }

    @Test
    public void hello() {
        httpMixIn.postResourceAndTestXML(ENDPOINT_URL, "/xml/hello-request.xml", "/xml/hello-response.xml");
    }

}
