package com.bugbd.omdb;

import android.util.Log;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class SSLExeption implements X509TrustManager {



    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        Log.d("SSLDebug","checkClientTrusted");
    }
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        Log.d("SSLDebug","checkServerTrusted");
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        Log.d("SSLDebug","X509Certificate");
        return new X509Certificate[0];
    }
}