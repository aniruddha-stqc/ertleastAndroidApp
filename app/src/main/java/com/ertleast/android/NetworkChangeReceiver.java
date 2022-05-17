package com.ertleast.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {
    ConnectionChangeCallback connectionChangeCallback;

    // Definition of interface for connection change callback
    public interface ConnectionChangeCallback {
        void onConnectionChange(boolean isConnected);
    }

    // Define the connection change call back enabler
    public void setConnectionChangeCallback(ConnectionChangeCallback
                                                    connectionChangeCallback) {
        this.connectionChangeCallback = connectionChangeCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Following code triggers only when network connectivity changed
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            // Get system service object.
            Object systemServiceObj = context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // Convert the service object to ConnectivityManager instance.
            ConnectivityManager connectivityManager = (ConnectivityManager) systemServiceObj;
            // Get network info object.
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            // Check whether network is available or not.
            boolean networkIsAvailable = false;
            // Check if network info object is instantiated
            if (networkInfo != null) {
                // Check if network is available
                if (networkInfo.isAvailable()) {
                    // Set network availability flag
                    networkIsAvailable = true;
                }
            }
            // Check if connection change call back object is instantiated
            if (connectionChangeCallback != null) {
                // Set the connection change call back listener
                connectionChangeCallback.onConnectionChange(networkIsAvailable);
            }
        }

    }
}