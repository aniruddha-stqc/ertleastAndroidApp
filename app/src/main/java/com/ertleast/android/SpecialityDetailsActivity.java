package com.ertleast.android;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.HashMap;

public class SpecialityDetailsActivity extends AppCompatActivity {
    Intent starterIntent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        starterIntent = getIntent();
        String uri = starterIntent.getStringExtra("speciality_uri");
        String name = starterIntent.getStringExtra("speciality_name");

        setContentView(R.layout.layout_webpagedetails);
        setTitle(name);

        WebView webView = findViewById(R.id.webpagedetails_webview);

        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setUserAgentString("com.biogenics.mobile");
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("AuthorizationToken", "demotoken");
        webView.loadUrl(uri, headers);
        //webView.loadUrl(uri);

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                request.setMimeType(mimeType);
                //------------------------COOKIE!!------------------------
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                //------------------------COOKIE!!------------------------
                request.addRequestHeader("User-Agent", userAgent);
                //request.setDescription("Downloading file...");
                if(haveStoragePermission()){
                    request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();

                }
                else {
                    //Toast.makeText(getApplicationContext(), "Download not allowed", Toast.LENGTH_LONG).show();

                }
            }
        });



    }

    public  boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Log.e("Permission error","You have permission");
                return true;
            } else {

               // Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
           // Log.e("Permission error","You already have the permission");
            return true;
        }
    }
}
