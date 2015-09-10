package com.redhat.samples.switchyard.ws;

import java.text.SimpleDateFormat;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Endpoint;

import org.apache.cxf.annotations.EndpointProperties;
import org.apache.cxf.annotations.EndpointProperty;
import org.apache.log4j.Logger;

import com.redhat.samples.switchyard.DateUtils;

@WebService(serviceName = "PingService", portName = "PingServicePort", wsdlLocation = "/PingService.wsdl")
@EndpointProperties({ @EndpointProperty(
        key = "ws-security.callback-handler",
        value = "com.redhat.samples.switchyard.ws.ServerPasswordCallback") })
public class PingServiceImpl implements PingService {

    private static final Logger LOGGER = Logger.getLogger(PingServiceImpl.class);

    public static final String ENDPOINT_URL = "http://localhost:18080/ping";

    @Override
    @WebMethod
    public XMLGregorianCalendar ping(XMLGregorianCalendar time) {
        LOGGER.info("ping = "
                + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(time.toGregorianCalendar().getTime()));
        return DateUtils.now();
    }

    public static void main(String[] args) {
        try {
            Endpoint.publish(ENDPOINT_URL, new PingServiceImpl());
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

}
