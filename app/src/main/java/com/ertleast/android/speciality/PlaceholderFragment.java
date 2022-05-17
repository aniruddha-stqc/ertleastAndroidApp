package com.ertleast.android.speciality;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ertleast.android.R;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private String[][] mspecialityArray;

    public static PlaceholderFragment newInstance(int index,String[][] specialityArray) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putSerializable("list", specialityArray);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;


        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            mspecialityArray = (String[][]) getArguments().getSerializable("list");

            //Switch case


        }
        pageViewModel.setIndex(index);
        pageViewModel.setSpecialityArray(mspecialityArray);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_speciality, container, false);

        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                String speciality_uri = s;
                //textView.setText(s);
                //Webview comes here
                WebView speciality_webview = root.findViewById(R.id.speciality_webview);
                WebSettings webSettings = speciality_webview.getSettings();
                webSettings.setJavaScriptEnabled(true);
                speciality_webview.setWebViewClient(new WebViewClient());
                speciality_webview.getSettings().setUserAgentString("Android");
                speciality_webview.loadUrl(speciality_uri);

                speciality_webview.setDownloadListener(new DownloadListener() {
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
                            DownloadManager dm = (DownloadManager) root.getContext().getSystemService(DOWNLOAD_SERVICE);
                            dm.enqueue(request);
                            Toast.makeText(root.getContext(), "Downloading File", Toast.LENGTH_LONG).show();

                        }
                        else {
                            //Toast.makeText(getApplicationContext(), "Download not allowed", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }

        });

        /*WebView speciality_webview = root.findViewById(R.id.speciality_webview);
        WebSettings webSettings = speciality_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        speciality_webview.loadUrl(speciality_uri);*/

        /*String uri = "https://bit.ly/2tslluf";
        final WebviewObject webviewObject = new WebviewObject(root, getActivity(), uri, R.id.drug_webview);
        SwipeRefreshLayout swipe;
        swipe = root.findViewById(R.id.swipeToRefresh_chat);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                webviewObject.Display();
            }
        });
        webviewObject.Display();
*/


        return root;
    }

    private boolean haveStoragePermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
               // Log.e("Permission error","You have permission");
                return true;
            } else {

                //Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
            //Log.e("Permission error","You already have the permission");
            return true;
        }
    }
}