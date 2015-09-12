package com.redhat.samples.switchyard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
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
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.switchyard.component.bean.Service;

@Service(value = PingService.class, name = "RedirectSoapPingService")
public class RedirectSoapPingServiceBean implements PingService {

	private static final URL WSDL = RedirectSoapPingServiceBean.class
			.getResource("/META-INF/PingService.wsdl");
	private static final QName SERVICE = new QName(
			"http://ws.switchyard.samples.redhat.com/", "PingService");
	private static final String TRUSTPASS = "secret";

	private static final String ENDPOINT = "https://example.com:8443/sample-ws/soap/ping";

	// private static final String ENDPOINT =
	// "http://localhost:8080/sample-ws/redirect";

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
		XMLGregorianCalendar ping = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(now);
		return ping;
	}

	private com.redhat.samples.switchyard.ws.PingService createService() {
		com.redhat.samples.switchyard.ws.PingService service = javax.xml.ws.Service
				.create(WSDL, SERVICE).getPort(
						com.redhat.samples.switchyard.ws.PingService.class);
		Map<String, Object> context = ((BindingProvider) service)
				.getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ENDPOINT);

		// enableAutoRedirect(service);
		setupSsl(service);

		return service;
	}

	private void setupSsl(Object port) {
		HTTPConduit httpConduit = (HTTPConduit) ClientProxy.getClient(port)
				.getConduit();
		
		final String certAlias = "host1_alias";
		
		TLSClientParameters tlsParams = new TLSClientParameters();
		tlsParams.setDisableCNCheck(true);
		tlsParams.setSecureSocketProtocol("TLSv1.2");
		
		tlsParams.setCertAlias(certAlias);

		// Set up the truststore for CXF

		KeyStore trustStore;
		try {
			trustStore = KeyStore.getInstance("JKS");

			File truststoreFile = new File(
					"/home/jshepher/Documents/FUSEDOC-324/host2.keystore.jks");
			trustStore.load(new FileInputStream(truststoreFile),
					TRUSTPASS.toCharArray());
			TrustManagerFactory trustFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustFactory.init(trustStore);
			TrustManager[] tm = trustFactory.getTrustManagers();
			tlsParams.setTrustManagers(tm);
		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException e) {
			throw new IllegalArgumentException(e);
		}

		// Set up the keystore for CXF
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			String keypass = "secret";
			File keystoreFile = new File(
					"/home/jshepher/Documents/FUSEDOC-324/host2.keystore.jks");
			keyStore.load(new FileInputStream(keystoreFile),
					keypass.toCharArray());
			KeyManagerFactory keyFactory = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyFactory.init(keyStore, TRUSTPASS.toCharArray());
			KeyManager[] km = keyFactory.getKeyManagers();
			tlsParams.setKeyManagers(km);
		} catch (NoSuchAlgorithmException | CertificateException | IOException
				| UnrecoverableKeyException | KeyStoreException e) {
			throw new IllegalArgumentException(e);
		}

		FiltersType filter = new FiltersType();
		// I don't think we should include export filters,
		// filter.getInclude().add(".*_EXPORT_.*");
		filter.getInclude().add("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
		filter.getInclude().add("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256");
		filter.getInclude().add("TLS_DHE_RSA_WITH_AES_128_GCM_SHA256");
		filter.getInclude().add("TLS_DHE_DSS_WITH_AES_128_GCM_SHA256");
		filter.getExclude().add(".*_DH_anon_.*");
		tlsParams.setCipherSuitesFilter(filter);
	}

	/*
	 * private void enableAutoRedirect(Object port) { HTTPConduit httpConduit =
	 * (HTTPConduit) ClientProxy.getClient(port).getConduit(); HTTPClientPolicy
	 * policy = new HTTPClientPolicy(); policy.setAutoRedirect(true);
	 * httpConduit.setClient(policy); }
	 */

}
