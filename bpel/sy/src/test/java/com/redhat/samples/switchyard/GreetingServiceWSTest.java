package com.redhat.samples.switchyard;

import javax.xml.ws.Endpoint;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.component.test.mixins.http.HTTPMixIn;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;
import org.switchyard.transform.config.model.TransformSwitchYardScanner;

import com.redhat.samples.switchyard.ws.GreetingService;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(
        config = SwitchYardTestCaseConfig.SWITCHYARD_XML,
        scanners = TransformSwitchYardScanner.class,
        mixins = { CDIMixIn.class, HTTPMixIn.class })
public class GreetingServiceWSTest {

    private static final String SY_ENDPOINT = "http://localhost:8080/sample/GreetingService";
    private static final String WS_ENDPOINT = "http://localhost:18080/greeting";

    private static Endpoint endpoint;

    private HTTPMixIn httpMixIn;

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
    public void hello() {
        httpMixIn.setContentType("application/soap+xml");
        httpMixIn.postResourceAndTestXML(SY_ENDPOINT, "/xml/soap-hello-request.xml", "/xml/soap-hello-response.xml");
    }

    @Test
    public void goodbye() {
        httpMixIn.setContentType("application/soap+xml");
        httpMixIn
                .postResourceAndTestXML(SY_ENDPOINT, "/xml/soap-goodbye-request.xml", "/xml/soap-goodbye-response.xml");
    }

}
