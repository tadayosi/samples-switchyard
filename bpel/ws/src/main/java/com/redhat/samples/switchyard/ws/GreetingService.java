package com.redhat.samples.switchyard.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;

@WebService(serviceName = "GreetingService")
public class GreetingService {

    private static final Logger LOGGER = Logger.getLogger(GreetingService.class);

    @WebMethod
    public String hello(@WebParam(name = "name") String name) {
        String message = String.format("Hello, %s!", name);
        LOGGER.info(message);
        return message;
    }

    @WebMethod
    public String goodbye(@WebParam(name = "name") String name) {
        String message = String.format("Goodbye, %s!", name);
        LOGGER.info(message);
        return message;
    }

    public static void main(String[] args) {
        try {
            Endpoint.publish("http://localhost:18080/greeting", new GreetingService());
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

}
