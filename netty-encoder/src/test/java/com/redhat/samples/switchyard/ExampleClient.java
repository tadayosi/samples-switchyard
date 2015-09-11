package com.redhat.samples.switchyard;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.switchyard.remote.RemoteMessage;
import org.switchyard.remote.http.HttpInvoker;

public class ExampleClient {

    private static final String URL = "http://localhost:8080/switchyard-remote";
    private static final QName SERVICE = new QName("urn:com.example.switchyard:netty-encoder:1.0", "ExampleService");

    private static final byte[] MESSAGE = "Hello!".getBytes();

    private HttpInvoker invoker;

    public ExampleClient() {
        invoker = new HttpInvoker(URL);
    }

    public void invokeService() throws IOException {
        RemoteMessage message = new RemoteMessage().setService(SERVICE).setContent(MESSAGE);
        invoker.invoke(message);
    }

    public static void main(String[] args) throws Exception {
        TCPServer server = new TCPServer(18080);
        try {
            server.start();
            new ExampleClient().invokeService();
            Thread.sleep(1000);
        } finally {
            server.stop();
        }
    }

}
