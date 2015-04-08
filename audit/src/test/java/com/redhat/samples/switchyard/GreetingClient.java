package com.redhat.samples.switchyard;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.switchyard.common.codec.Base64;
import org.switchyard.component.soap.util.SOAPUtil;
import org.switchyard.component.test.mixins.http.HTTPMixIn;

public class GreetingClient {

    private static final String ENDPOINT_URL = "http://localhost:8080/sample/GreetingService";

    private static final String USERNAME = "kermit";
    private static final String PASSWORD = "the-frog-1";

    public static void main(String[] args) throws IOException {
        HTTPMixIn httpMixIn = new HTTPMixIn();
        httpMixIn.initialize();
        try {
            //@formatter:off
            String response = httpMixIn
                    .setRequestHeader("Authorization", "Basic " + Base64.encodeFromString(USERNAME + ":" + PASSWORD))
                    .postString(ENDPOINT_URL, IOUtils.toString(GreetingClient.class.getResource("/xml/hello-request.xml")));
            //@formatter:on
            SOAPUtil.prettyPrint(response, System.out);
        } finally {
            httpMixIn.uninitialize();
        }
    }

}
