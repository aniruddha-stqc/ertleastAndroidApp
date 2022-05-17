package com.ertleast.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WebViewCookieFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Client's Live URL comes here
        //String uri = "http://freetesting.site/bio_web/specialty.php?by=1";
        //Client's Live URL comes here

        //Under construction URL comes here
        String uri = "https://bit.ly/2tslluf";
        //Under construction URL comes here

        //test URL in Client's server comes here
        //String uri = "http://freetesting.site/bio_mobile/android/test.php";
        //test URL in Client's server

        /// Local test URL comes here
        //String uri = "http://192.168.43.122/biogenics/BioGenics/bio_mobile/android/test.php";
        /// Local test URL comes here


        //This code remains unchanged
        View view = inflater.inflate(R.layout.fragment_drug, container, false);
        WebView webView = view.findViewById(R.id.drug_webview);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        //webView.addJavascriptInterface(new WebAppInterface(getActivity()), "HandOverToAndroid");
        //This code remains unchanged


        webView.loadUrl(uri);
         //This code remains unchanged
        return view;
        //This code remains unchanged
    }
}
