package com.redhat.samples.switchyard;

import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.switchyard.component.bean.Service;

@Service(value = PingService.class, name = "RedirectSoapPingService")
public class RedirectSoapPingServiceBean implements PingService {

    private static final URL WSDL = RedirectSoapPingServiceBean.class.getResource("/PingService.wsdl");
    private static final QName SERVICE = new QName("http://ws.switchyard.samples.redhat.com/", "PingService");
    //private static final String ENDPOINT = "http://localhost:8080/sample-ws/soap/ping";
    private static final String ENDPOINT = "http://localhost:8080/sample-ws/redirect";

    @Override
    public String ping() {
        com.redhat.samples.switchyard.ws.PingService service = createService();
        try {
            XMLGregorianCalendar pong = service.ping(now());
            return pong.toXMLFormat();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private XMLGregorianCalendar now() throws DatatypeConfigurationException {
        GregorianCalendar now = new GregorianCalendar();
        now.setTime(new Date());
        XMLGregorianCalendar ping = DatatypeFactory.newInstance().newXMLGregorianCalendar(now);
        return ping;
    }

    private com.redhat.samples.switchyard.ws.PingService createService() {
        com.redhat.samples.switchyard.ws.PingService service = javax.xml.ws.Service.create(WSDL, SERVICE).getPort(
                com.redhat.samples.switchyard.ws.PingService.class);
        Map<String, Object> context = ((BindingProvider) service).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ENDPOINT);

        enableAutoRedirect(service);

        return service;
    }

    private void enableAutoRedirect(Object port) {
        HTTPConduit httpConduit = (HTTPConduit) ClientProxy.getClient(port).getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setAutoRedirect(true);
        httpConduit.setClient(policy);
    }

}
