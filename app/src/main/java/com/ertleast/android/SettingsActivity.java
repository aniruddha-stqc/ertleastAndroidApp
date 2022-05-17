package com.ertleast.android;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {
    Intent starterIntent;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String current_language = LocaleHelper.getLanguage(this);

        boolean switch_status = false;
        if (current_language.equals("th")) {
            switch_status = true;
        }


        setContentView(R.layout.layout_settings);
        setTitle(R.string.settings);

        final SwitchCompat switchCompat = findViewById(R.id.change_lang);

        switchCompat.setChecked(switch_status);
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                if (switchCompat.isChecked()) {
                    LocaleHelper.setLocale(SettingsActivity.this, "th");

                    Context context = LocaleHelper.setLocale(SettingsActivity.this, "th");
                    Resources resources = context.getResources();
                    //setTitle(resources.getString(R.string.settings));
                    setTitle(resources.getString(R.string.language_setting));
                    switchCompat.setText(resources.getString(R.string.translation));

                } else {
                    LocaleHelper.setLocale(SettingsActivity.this, "en");

                    Context context = LocaleHelper.setLocale(SettingsActivity.this, "en");
                    Resources resources = context.getResources();
                    //setTitle(resources.getString(R.string.settings));
                    setTitle(resources.getString(R.string.language_setting));
                    switchCompat.setText(resources.getString(R.string.translation));


                }

            }

        });
    }


}
