package com.crawlers.core;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.*;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.security.SecureRandom;

public class HttpClientFactory {

    private static CloseableHttpClient client;

    private static PoolingHttpClientConnectionManager connPool = null;

    //private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientFactory.class);

    static{
        try
        {
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
            //sslcontext.init(new KeyManager[0], new TrustManager[] { new HttpsTrustManager() }, new SecureRandom());
            sslcontext.init(null, new X509TrustManager[]{new HttpsTrustManager()}, new SecureRandom());
            SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext,
                    new HostnameVerifier() {
                        @Override
                        public boolean verify(final String s, final SSLSession sslSession) {

                            return true;
                        }
                    });

            Registry r = RegistryBuilder. create()
                    .register("https", factory).build();

            connPool = new PoolingHttpClientConnectionManager(r);
            // Increase max total connection to 200
            connPool.setMaxTotal(200);

            connPool.setDefaultMaxPerRoute(20);


            client = HttpClients.custom().
                    setConnectionManagerShared(true).
                    setConnectionManager(connPool).
                    setSSLSocketFactory(factory).build();
        }
        catch(Exception e){
           // LOGGER.error("Error initiliazing HttpClientFactory :: ",e);
        }
    }

    public static CloseableHttpClient getHttpsClient() throws KeyManagementException, NoSuchAlgorithmException  {

        if (client != null) {
            return client;
        }
        throw new RuntimeException("Client is not initiliazed properly");

    }
    public static void releaseInstance() {
        client = null;
    }
}