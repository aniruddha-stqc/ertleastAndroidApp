package com.ertleast.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebpageDetailsActivity extends AppCompatActivity {
    Intent starterIntent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        starterIntent = getIntent();
        String uri = starterIntent.getStringExtra("calculator_uri");
        String name = starterIntent.getStringExtra("calculator_name");

        setContentView(R.layout.layout_webpagedetails);
        setTitle(name);

        WebView webView = findViewById(R.id.webpagedetails_webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(uri);

    }


}
