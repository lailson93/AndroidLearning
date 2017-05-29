package com.example.lailson.testelogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ActWeb extends AppCompatActivity {

    private WebView webId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_web);

        webId = (WebView) findViewById(R.id.webId);
        webId.setWebChromeClient(new WebChromeClient());
        WebSettings ws = webId.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);
        ws.setDomStorageEnabled(true);




        webId.loadUrl("http://www.google.com");
    }



}
