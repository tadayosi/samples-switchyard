package com.redhat.samples.switchyard;

import java.net.URL;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.cxf.ws.security.SecurityConstants;
import org.switchyard.component.bean.Service;

@Service(value = PingService.class, name = "SoapPingClientBean")
public class SoapPingClientBean implements PingService {

    private static final URL WSDL = SoapPingClientBean.class.getResource("/PingService.wsdl");
    private static final QName SERVICE = new QName("http://ws.switchyard.samples.redhat.com/", "PingService");
    private static final String ENDPOINT = "http://localhost:18080/ping";

    private static final String USERNAME = "kermit";
    private static final String PASSWORD_CALLBACK = ClientPasswordCallback.class.getName();

    @Override
    public String ping() {
        com.redhat.samples.switchyard.ws.PingService service = createService();
        XMLGregorianCalendar pong = service.ping(DateUtils.now());
        return pong.toXMLFormat();
    }

    private com.redhat.samples.switchyard.ws.PingService createService() {
        com.redhat.samples.switchyard.ws.PingService service = javax.xml.ws.Service.create(WSDL, SERVICE).getPort(
                com.redhat.samples.switchyard.ws.PingService.class);
        Map<String, Object> context = ((BindingProvider) service).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ENDPOINT);
        context.put(SecurityConstants.USERNAME, USERNAME);
        context.put(SecurityConstants.CALLBACK_HANDLER, PASSWORD_CALLBACK);
        return service;
    }

}
