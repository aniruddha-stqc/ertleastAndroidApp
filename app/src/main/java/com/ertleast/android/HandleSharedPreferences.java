package com.ertleast.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class HandleSharedPreferences {
    public static String getPersistedData(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void persist(Context context, String key, String data) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, data);
        editor.apply();
    }
}
