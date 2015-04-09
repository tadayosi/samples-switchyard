package com.redhat.samples.switchyard.ws;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

@SuppressWarnings("restriction")
public class ServicePublisher {

    private static final Logger LOGGER = Logger.getLogger(ServicePublisher.class);

    private static final String KEYSTORE = "server.jks";
    private static final String KEYSTORE_PASSWORD = "password";

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 18443;
    private static final String PATH = "/soap/ping";

    private HttpsServer httpsServer;
    private Endpoint endpoint;

    public static void main(String[] args) {
        try {
            new ServicePublisher().publish();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ServicePublisher() throws Exception {
        LOGGER.info("keystore = " + KEYSTORE);
        LOGGER.info(String.format("url = https://%s:%s%s", HOSTNAME, PORT, PATH));
        httpsServer = HttpsServer.create(new InetSocketAddress(HOSTNAME, PORT), 0);
        httpsServer.setHttpsConfigurator(createHttpsConfigurator());
        endpoint = Endpoint.create(new PingService());
    }

    private HttpsConfigurator createHttpsConfigurator() throws Exception {
        KeyStore keyStore = loadKeyStore();
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(createKeyManagerFactory(keyStore).getKeyManagers(), createTrustManagerFactory(keyStore)
                .getTrustManagers(), null);
        return new HttpsConfigurator(sslContext);
    }

    private KeyStore loadKeyStore() throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException, FileNotFoundException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(KEYSTORE), KEYSTORE_PASSWORD.toCharArray());
        return keyStore;
    }

    private KeyManagerFactory createKeyManagerFactory(KeyStore keyStore) throws NoSuchAlgorithmException,
            KeyStoreException, UnrecoverableKeyException {
        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        factory.init(keyStore, KEYSTORE_PASSWORD.toCharArray());
        return factory;
    }

    private TrustManagerFactory createTrustManagerFactory(KeyStore keyStore) throws NoSuchAlgorithmException,
            KeyStoreException {
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(keyStore);
        return factory;
    }

    public void publish() {
        httpsServer.start();
        endpoint.publish(httpsServer.createContext(PATH));
    }

}
