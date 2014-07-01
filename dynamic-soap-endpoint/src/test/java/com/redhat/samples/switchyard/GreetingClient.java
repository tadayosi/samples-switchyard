package com.redhat.samples.switchyard;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.switchyard.Scope;
import org.switchyard.remote.RemoteMessage;
import org.switchyard.remote.http.HttpInvoker;

import com.google.common.base.Strings;
import com.google.common.io.CharStreams;

public class GreetingClient {

    private static final String URL = "http://localhost:8080/switchyard-remote";
    private static final QName SERVICE = new QName("urn:samples-switchyard:dynamic-soap-endpoint:1.0",
            "DynamicSoapRouter");

    public static void main(String[] args) throws Exception {
        HttpInvoker invoker = new HttpInvoker(URL);
        invoke(invoker, "hello", "/xml/request-hello.xml");
        invoke(invoker, "goodbye", "/xml/request-goodbye.xml");
    }

    private static void invoke(HttpInvoker invoker, String label, String requestPath) throws IOException {
        RemoteMessage message = new RemoteMessage();
        String request = IOUtils.toString(GreetingClient.class.getResource(requestPath));
        message.setService(SERVICE).setContent(request);
        message.getContext().setProperty(DynamicSoapRouterRoute.ENDPOINT_LABEL, label, Scope.EXCHANGE);
        RemoteMessage reply = invoker.invoke(message);
        if (reply.isFault()) {
            Object error = reply.getContent();
            if (error instanceof Exception) ((Exception) error).printStackTrace();
            else System.err.println("ERROR: " + error);
            return;
        }
        System.out.println(Strings.repeat("*", 80));
        System.out.println(CharStreams.toString(new InputStreamReader((InputStream) reply.getContent())));
        System.out.println(Strings.repeat("*", 80));
    }

}
