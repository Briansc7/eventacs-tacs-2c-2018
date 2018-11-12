package com.eventacs.external.telegram.client.httprequest;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.KeyStore;

public class SecureSSL {

    private static final String KEYSTOREPATH = "./oauth-authorization-server/src/main/resources/eventacskeystore.p12";
    private static final String KEYSTOREPASS = "eventacs";
    private static final String KEYPASS = "eventacs";


//    // Allow TLSv1 protocol only
//    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//            sslcontext,
//            new String[] { "TLSv1" },
//            null,
//            SSLConnectionSocketFactory.getDefaultHostnameVerifier());
//
//    CloseableHttpClient httpclient = HttpClients.custom()
//            .setSSLSocketFactory(sslsf)
//            .build();

    KeyStore readStore() throws Exception {
        System.out.println("Directorio: " + Paths.get(".").toAbsolutePath().normalize().toString());
        try (InputStream keyStoreStream = new FileInputStream(new File(KEYSTOREPATH))) { //this.getClass().getResourceAsStream(KEYSTOREPATH)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(keyStoreStream, KEYSTOREPASS.toCharArray());
            return keyStore;
        }
    }

    public SSLContext getSSLContext(){
        SSLContext sslContext = null;
        try {
//            SSLContext sslcontext = SSLContexts.custom()
//                    .loadTrustMaterial(new File(KEYSTOREPATH), "nopassword".toCharArray(),
//                            new TrustSelfSignedStrategy())
//                    .build();
            sslContext = SSLContexts.custom()
                    .loadKeyMaterial(readStore(), KEYPASS.toCharArray())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext;
    }
}