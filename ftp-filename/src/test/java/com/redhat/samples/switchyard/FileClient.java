package com.redhat.samples.switchyard;

import javax.xml.namespace.QName;

import org.switchyard.remote.RemoteMessage;
import org.switchyard.remote.http.HttpInvoker;

public class FileClient {

    private static final String URL = "http://localhost:8080/switchyard-remote";
    private static final QName SERVICE = new QName("urn:samples-switchyard:ftp-filename:1.0", "FileService");

    public static void main(String[] args) throws Exception {
        HttpInvoker invoker = new HttpInvoker(URL);
        RemoteMessage message = new RemoteMessage();
        message.setService(SERVICE).setContent(FileClient.class.getSimpleName());
        invoker.invoke(message);
    }

}
