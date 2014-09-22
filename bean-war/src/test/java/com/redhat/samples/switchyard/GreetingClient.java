package com.redhat.samples.switchyard;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.switchyard.component.test.mixins.http.HTTPMixIn;

public class GreetingClient {

    private static final String ENDPOINT_URL = "http://localhost:8080/sample/GreetingService";

    public static void main(String[] args) throws IOException {
        HTTPMixIn httpMixIn = new HTTPMixIn();
        httpMixIn.initialize();
        try {
            String response = httpMixIn.postString(ENDPOINT_URL,
                    IOUtils.toString(GreetingClient.class.getResource("/xml/hello-request.xml")));
            System.out.println(response);
        } finally {
            httpMixIn.uninitialize();
        }
    }

}
