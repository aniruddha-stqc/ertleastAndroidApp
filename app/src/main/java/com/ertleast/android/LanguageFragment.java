package com.ertleast.android;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class LanguageFragment extends Fragment {


    public LanguageFragment() {
        // Required empty public constructor
    }

    private RadioButton radioThai;
    private RadioButton radioEnglish;
    private FrameLayout parentFrameLayout;
    private Button languageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        radioEnglish = view.findViewById(R.id.radioEnglish);
        radioThai = view.findViewById(R.id.radioThai);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
        languageButton = view.findViewById(R.id.button_language);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });

        radioThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setFragment(new SignInFragment());
                LocaleHelper.setLocale(getActivity(), "th");

                Context context = LocaleHelper.setLocale(getActivity(), "th");
                Resources resources = context.getResources();
                languageButton.setText(resources.getString(R.string.button_language));
            }
        });

        radioEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setFragment(new SignInFragment());
                LocaleHelper.setLocale(getActivity(), "en");

                Context context = LocaleHelper.setLocale(getActivity(), "en");
                Resources resources = context.getResources();
                languageButton.setText(resources.getString(R.string.button_language));
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

}
