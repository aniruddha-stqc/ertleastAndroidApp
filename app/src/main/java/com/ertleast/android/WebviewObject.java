package com.ertleast.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewObject {
    WebView webView;
    Activity activity;
    String uri_string;
    int webview_id;

    WebviewObject(View p_view, Activity p_activity, String p_uri_string, int p_webview_id) {

        webView = p_view.findViewById(p_webview_id);

        activity = p_activity;
        uri_string = p_uri_string;
        webview_id = p_webview_id;
    }

    void Display() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
                view.loadUrl("about:blank");
                view.loadUrl("file:///android_asset/webview_error.html");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });
        webView.loadUrl(uri_string);

    }
}
