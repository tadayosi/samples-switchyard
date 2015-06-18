package com.redhat.samples.switchyard;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.switchyard.component.test.mixins.http.HTTPMixIn;

public class GreetingClient {

    private static String BASE_URL = "http://localhost:8080/sample";

    public static void main(String[] args) throws IOException {
        HTTPMixIn httpMixIn = new HTTPMixIn();
        httpMixIn.initialize();
        try {
            getAndPrintStatus(httpMixIn, BASE_URL, "/hello/Sample");
            getAndPrintStatus(httpMixIn, BASE_URL, "/goodbye/Sample");
        } finally {
            httpMixIn.uninitialize();
        }
    }

    private static void getAndPrintStatus(HTTPMixIn httpMixIn, String baseUrl, String path) throws IOException {
        HttpResponse response = httpMixIn.sendStringAndGetMethod(baseUrl + path, null, HTTPMixIn.HTTP_GET);
        int statusCode = response.getStatusLine().getStatusCode();
        String content = IOUtils.toString(response.getEntity().getContent());
        System.out.println(String.format("%s : [%s] %s", path, statusCode, content));
    }

}
