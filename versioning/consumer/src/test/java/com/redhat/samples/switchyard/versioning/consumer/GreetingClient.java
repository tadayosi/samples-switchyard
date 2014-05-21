package com.redhat.samples.switchyard.versioning.consumer;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.switchyard.remote.RemoteMessage;
import org.switchyard.remote.http.HttpInvoker;

public class GreetingClient {

    private static final String URL = "http://localhost:8080/switchyard-remote";
    private static final QName SERVICE = new QName("urn:samples-switchyard:versioning-consumer:1.0", "GreetingConsumer");

    public static void main(String[] args) throws Exception {
        HttpInvoker invoker = new HttpInvoker(URL);
        invoke(invoker, "/xml/hello-request_v1_0.xml");
        invoke(invoker, "/xml/hello-request_v1_1.xml");
        invoke(invoker, "/xml/goodbye-request_v1_1.xml");
    }

    private static void invoke(HttpInvoker invoker, String requestPath) throws IOException {
        RemoteMessage message = new RemoteMessage();
        String request = IOUtils.toString(GreetingClient.class.getResource(requestPath));
        message.setService(SERVICE).setContent(request);
        RemoteMessage reply = invoker.invoke(message);
        if (reply.isFault()) {
            Object error = reply.getContent();
            if (error instanceof Exception) ((Exception) error).printStackTrace();
            else System.err.println("ERROR: " + error);
            return;
        }
        System.out.println(reply.getContent());
    }

}
