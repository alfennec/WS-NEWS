package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.fennec.dailynews.R;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.config.UserJson;
import com.fennec.dailynews.config.WeatherJson;
import com.fennec.dailynews.entity.User;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;


public class SplashActivity extends AppCompatActivity {

    public static int time_splash = 5000;
    public static SplashActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        main=this;

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                SplashActivity.this.finish();
            }
        }, time_splash);

        //WeatherJson WeatherJson = new WeatherJson("Berkane" , main, "get", 1);


        Ion.getDefault(main).getHttpClient().getSSLSocketMiddleware().setTrustManagers(new TrustManager[] {new X509TrustManager() {

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }});

    }
}
