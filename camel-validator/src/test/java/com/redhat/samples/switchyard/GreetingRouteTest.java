package com.redhat.samples.switchyard;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;
import org.switchyard.test.SwitchYardTestKit;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML, mixins = { CDIMixIn.class })
public class GreetingRouteTest {

    private static final File INVALID_DIR = new File("target/invalid");

    private SwitchYardTestKit testKit;

    @ServiceOperation("GreetingRoute")
    private Invoker route;

    @Before
    public void cleanInvalidDirectory() {
        if (INVALID_DIR.exists()) {
            for (File f : INVALID_DIR.listFiles()) {
                f.delete();
            }
        }
    }

    @Test
    public void ok() {
        String request = testKit.readResourceString("/xml/hello-request.xml");
        String response = route.operation("").sendInOut(request).getContent(String.class);

        testKit.compareXMLToResource(response, "/xml/hello-response.xml");
        assertThat(INVALID_DIR.list().length, is(0));
    }

    @Test
    public void invalid() {
        String request = testKit.readResourceString("/xml/hello-request-invalid.xml");
        String response = route.operation("").sendInOut(request).getContent(String.class);

        assertThat(response, startsWith("ERROR: "));
        assertThat(INVALID_DIR.list().length, is(1));
    }

}
