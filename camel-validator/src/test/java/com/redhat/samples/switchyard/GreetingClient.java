package com.redhat.samples.switchyard;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.switchyard.component.test.mixins.http.HTTPMixIn;

public class GreetingClient {

    private static final String ENDPOINT = "http://localhost:8080/sample/greeting";

    public static void main(String[] args) throws IOException {
        String name = GreetingClient.class.getSimpleName();
        HTTPMixIn httpMixIn = new HTTPMixIn();
        httpMixIn.initialize();
        try {
            requestReply(httpMixIn, ENDPOINT, name);
        } finally {
            httpMixIn.uninitialize();
        }
    }

    private static void requestReply(HTTPMixIn httpMixIn, String url, String request) throws IOException {
        HttpResponse response = httpMixIn.sendStringAndGetMethod(url, request, HTTPMixIn.HTTP_POST);
        int statusCode = response.getStatusLine().getStatusCode();
        String content = IOUtils.toString(response.getEntity().getContent());
        System.out.println(String.format("[%s] %s", statusCode, content));
    }

}
