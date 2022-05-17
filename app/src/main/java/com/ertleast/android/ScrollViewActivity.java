package com.ertleast.android;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScrollViewActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String message = intent.getStringExtra("scan_info");
        setContentView(R.layout.activity_scroll);
        setTitle(R.string.scan_info);

        TextView mMessageWindow = findViewById(R.id.messageWindow);

        mMessageWindow.setText(message);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_browse);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Intent intent = new Intent(ScrollViewActivity.this, BrowseActivity.class);
                intent.putExtra("scanned_uri", message);
                startActivity(intent);*/
                Toast.makeText(ScrollViewActivity.this, "Copied !", Toast.LENGTH_SHORT).show();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied_scan_info", message);
                clipboard.setPrimaryClip(clip);
            }
        });
    }
}