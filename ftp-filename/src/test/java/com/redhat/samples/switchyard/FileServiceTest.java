package com.redhat.samples.switchyard;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.ftpserver.ftplet.FtpException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.switchyard.component.test.mixins.cdi.CDIMixIn;
import org.switchyard.test.Invoker;
import org.switchyard.test.ServiceOperation;
import org.switchyard.test.SwitchYardRunner;
import org.switchyard.test.SwitchYardTestCaseConfig;

import com.google.common.io.Files;

@RunWith(SwitchYardRunner.class)
@SwitchYardTestCaseConfig(config = SwitchYardTestCaseConfig.SWITCHYARD_XML, mixins = { CDIMixIn.class })
public class FileServiceTest {

    @ServiceOperation("FileService")
    private Invoker service;

    private static FTPServer ftpServer;

    @BeforeClass
    public static void startFtpServer() throws FtpException {
        ftpServer = new FTPServer().start();
    }

    @AfterClass
    public static void stopFtpServer() {
        ftpServer.stop();
    }

    @Before
    public void setUp() {
        new File("target/ftp/out", FileRoute.OUT_FILE).delete();
    }

    @Test
    public void test() throws Exception {
        String message = getClass().getSimpleName();
        service.sendInOnly(message);
        Thread.sleep(1000);

        File outFile = new File("target/ftp/out", FileRoute.OUT_FILE);
        assertThat(outFile.exists(), is(true));
        assertThat(Files.toString(outFile, Charset.defaultCharset()), is("<<< " + message + " >>>"));
    }

}
