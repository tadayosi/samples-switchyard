package com.redhat.samples.switchyard;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML, mixins = { CDIMixIn.class })
public class ExampleServiceTest {

    private static final byte[] MESSAGE = "Hello!".getBytes();

    private static TCPServer TCP_SERVER;

    @ServiceOperation("ExampleService")
    private Invoker service;

    @BeforeClass
    public static void startServer() {
        TCP_SERVER = new TCPServer(18080);
        TCP_SERVER.start();
    }

    @AfterClass
    public static void stopServer() {
        TCP_SERVER.stop();
        TCP_SERVER = null;
    }

    @Test
    public void sendBytes() throws Exception {
        service.operation("process").sendInOnly(MESSAGE);
        Thread.sleep(1000);
        assertThat(TCP_SERVER.receivedMessage(), is(MESSAGE));
    }

}
