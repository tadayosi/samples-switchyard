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
                getAndPrintStatus(httpMixIn, PROXY_URL_BEAN, "/hello?name=" + name);
                getAndPrintStatus(httpMixIn, PROXY_URL_CAMEL, "/hello?name=" + name);
                postAndPrintStatus(httpMixIn, PROXY_URL_BEAN, "/goodbye", name);
                postAndPrintStatus(httpMixIn, PROXY_URL_CAMEL, "/goodbye", name);
            }
        } finally {
            httpMixIn.uninitialize();
        }
    }

    private static void getAndPrintStatus(HTTPMixIn httpMixIn, String baseUrl, String path) throws IOException {
        HttpResponse response = httpMixIn.sendStringAndGetMethod(baseUrl + path, null, HTTPMixIn.HTTP_GET);
        System.out.println(String.format("%s : [%s] %s", path, response.getStatusLine().getStatusCode(),
                IOUtils.toString(response.getEntity().getContent())));
    }

    private static void postAndPrintStatus(HTTPMixIn httpMixIn, String baseUrl, String path, String post)
            throws IOException {
        HttpResponse response = httpMixIn.sendStringAndGetMethod(baseUrl + path, post, HTTPMixIn.HTTP_POST);
        System.out.println(String.format("%s (%s) : [%s] %s", path, post, response.getStatusLine().getStatusCode(),
                IOUtils.toString(response.getEntity().getContent())));
    }

}
