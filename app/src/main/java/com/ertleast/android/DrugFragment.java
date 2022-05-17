package com.ertleast.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DrugFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String uri = "https://bit.ly/2tslluf";
        View view = inflater.inflate(R.layout.fragment_drug, container, false);

        final WebviewObject webviewObject = new WebviewObject(view, getActivity(), uri, R.id.drug_webview);
        SwipeRefreshLayout swipe;
        swipe = view.findViewById(R.id.swipeToRefresh_drug);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                webviewObject.Display();
            }
        });
        webviewObject.Display();

        return view;

    }
}
