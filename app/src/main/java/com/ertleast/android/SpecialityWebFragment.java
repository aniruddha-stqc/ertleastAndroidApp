package com.ertleast.android;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

import static android.content.Context.DOWNLOAD_SERVICE;

public class SpecialityWebFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        String current_language = LocaleHelper.getLanguage(getContext());
        String uri = null;
        if(current_language.equals("th")){
            //uri = "http://thaipharmaindex.com/bio_web/speacility_mainthai.php";
            uri = "http://thaipharmaindex.com/speacility_mainthai.php";

        }
        else {
            //uri = "http://thaipharmaindex.com/bio_web/speacility_main.php";
            uri = "http://thaipharmaindex.com/speacility_main.php";
        }
        final View view = inflater.inflate(R.layout.fragment_speciality_web, container, false);
        WebView webView = view.findViewById(R.id.speciality_web_webview);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setUserAgentString("com.biogenics.mobile");
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("AuthorizationToken", "demotoken");
        webView.loadUrl(uri, headers);
        webView.loadUrl(uri);
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
                if (haveStoragePermission()) {
                    request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                    DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(container.getContext(), "Downloading File", Toast.LENGTH_LONG).show();

                } else {
                    //Toast.makeText(getApplicationContext(), "Download not allowed", Toast.LENGTH_LONG).show();

                }


            }
        });
        return view;
    }

    public boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Log.e("Permission error", "You have permission");
                return true;
            } else {

                //Log.e("Permission error", "You have asked for permission");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //you dont need to worry about these stuff below api level 23
            //Log.e("Permission error", "You already have the permission");
            return true;
        }
    }


}

