package com.redhat.samples.switchyard;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.switchyard.component.test.mixins.http.HTTPMixIn;

public class GreetingClient {

    private static final String PROXY_URL_BEAN = "http://localhost:8080/proxy-bean";
    private static final String PROXY_URL_CAMEL = "http://localhost:8080/proxy-camel";

    public static void main(String[] args) throws IOException {
        HTTPMixIn httpMixIn = new HTTPMixIn();
        httpMixIn.initialize();
        try {
            for (String name : Arrays.asList("Sample", "error", "not_found", "unauthorized")) {
                getAndPrintStatus(httpMixIn, PROXY_URL_BEAN, "/hello/" + name);
                getAndPrintStatus(httpMixIn, PROXY_URL_CAMEL, "/goodbye/" + name);
            }
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
