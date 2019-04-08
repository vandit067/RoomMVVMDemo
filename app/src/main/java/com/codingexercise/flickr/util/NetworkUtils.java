package com.codingexercise.flickr.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

/**
 * This class is use for perform network related operations.
 */
public class NetworkUtils {

    /**
     * Check network is available or not.
     *
     * @param context application context
     * @return boolean is network available or not.
     */
    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
