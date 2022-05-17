package com.ertleast.android;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class LanguageActivity extends AppCompatActivity {

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    private RadioButton radioThai;
    private RadioButton radioEnglish;
    private Button languageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        getSupportActionBar().hide(); //hide the title bar
        radioEnglish = findViewById(R.id.radioEnglish);
        radioThai = findViewById(R.id.radioThai);
        languageButton = findViewById(R.id.button_language);




        String logged_in = HandleSharedPreferences.getPersistedData(this, "logged_in");

        if (logged_in.contentEquals("true")){
            startActivity(new Intent(LanguageActivity.this, MainActivity.class));
        }
        else{
            LocaleHelper.setLocale(LanguageActivity.this, "th");
            Context context = LocaleHelper.setLocale(LanguageActivity.this, "th");
            Resources resources = context.getResources();
            languageButton.setText(resources.getString(R.string.button_language));

        }


        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LanguageActivity.this, RegisterActivity.class));
            }
        });

        radioThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setFragment(new SignInFragment());
                LocaleHelper.setLocale(LanguageActivity.this, "th");

                Context context = LocaleHelper.setLocale(LanguageActivity.this, "th");
                Resources resources = context.getResources();
                languageButton.setText(resources.getString(R.string.button_language));
            }
        });

        radioEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setFragment(new SignInFragment());
                LocaleHelper.setLocale(LanguageActivity.this, "en");

                Context context = LocaleHelper.setLocale(LanguageActivity.this, "en");
                Resources resources = context.getResources();
                languageButton.setText(resources.getString(R.string.button_language));
            }
        });
    }
}
