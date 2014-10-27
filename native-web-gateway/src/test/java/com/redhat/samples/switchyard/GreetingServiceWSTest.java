package com.redhat.samples.switchyard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.component.test.mixins.http.HTTPMixIn;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;
import org.switchyard.transform.config.model.TransformSwitchYardScanner;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(
        config = SwitchYardTestCaseConfig.SWITCHYARD_XML,
        scanners = TransformSwitchYardScanner.class,
        mixins = { CDIMixIn.class, HTTPMixIn.class })
public class GreetingServiceWSTest {

    private static final String ENDPOINT_URL = "http://localhost:18080/sample/GreetingService";

    private HTTPMixIn httpMixIn;

    @Test
    public void hello() {
        httpMixIn.setContentType("application/soap+xml");
        httpMixIn.postResourceAndTestXML(ENDPOINT_URL, "/xml/hello-request.xml", "/xml/hello-response.xml");
    }

    @Test
    public void goodbye() {
        httpMixIn.setContentType("application/soap+xml");
        httpMixIn.postResourceAndTestXML(ENDPOINT_URL, "/xml/goodbye-request.xml", "/xml/goodbye-response.xml");
    }

}
