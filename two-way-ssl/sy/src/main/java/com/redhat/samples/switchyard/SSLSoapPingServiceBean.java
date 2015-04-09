package com.redhat.samples.switchyard;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.KeyStore;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.switchyard.component.bean.Service;

@Service(value = PingService.class, name = "SSLSoapPingService")
public class SSLSoapPingServiceBean implements PingService {

    private static final URL WSDL = SSLSoapPingServiceBean.class.getResource("/META-INF/PingService.wsdl");
    private static final QName SERVICE = new QName("http://ws.switchyard.samples.redhat.com/", "PingService");
    private static final String ENDPOINT = "https://localhost:18443/soap/ping";

    private static final String KEYSTORE = System.getProperty("java.io.tmpdir") + "/client.jks";
    private static final String KEYSTORE_PASSWORD = "password";

    private com.redhat.samples.switchyard.ws.PingService service = createService();

    @Override
    public String ping() {
        try {
            synchronized (service) {
                XMLGregorianCalendar pong = service.ping(now());
                return pong.toXMLFormat();
            }
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

        configureSSL(service);

        return service;
    }

    private void configureSSL(com.redhat.samples.switchyard.ws.PingService service) {
        HTTPConduit conduit = (HTTPConduit) ClientProxy.getClient(service).getConduit();
        TLSClientParameters params = new TLSClientParameters();
        try {
            params.setTrustManagers(trustManagers());
            params.setKeyManagers(keyManagers());
            params.setCipherSuitesFilter(cipherSuitesFilter());
            conduit.setTlsClientParameters(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private KeyManager[] keyManagers() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(new File(KEYSTORE)), KEYSTORE_PASSWORD.toCharArray());
        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        factory.init(keyStore, KEYSTORE_PASSWORD.toCharArray());
        return factory.getKeyManagers();
    }

    private TrustManager[] trustManagers() throws Exception {
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(new FileInputStream(new File(KEYSTORE)), KEYSTORE_PASSWORD.toCharArray());
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(trustStore);
        return factory.getTrustManagers();
    }

    private FiltersType cipherSuitesFilter() {
        FiltersType filter = new FiltersType();
        filter.getInclude().add(".*_EXPORT_.*");
        filter.getInclude().add(".*_EXPORT1024_.*");
        filter.getInclude().add(".*_WITH_DES_.*");
        filter.getInclude().add(".*_WITH_AES_.*");
        filter.getInclude().add(".*_WITH_NULL_.*");
        filter.getExclude().add(".*_DH_anon_.*");
        return filter;
    }

}
