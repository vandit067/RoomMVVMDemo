package com.codingexercise.flickr.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.codingexercise.flickr.R;

import androidx.annotation.NonNull;

public enum FlickrSharedPreference {
    INSTANCE;

    private static final String KEY_IS_DATA_INSERTED_IN_DB = "key_is_data_inserted_in_db";

    public void saveIsDataInsertedInDb(@NonNull Context appContext, boolean isDataInsertedInDb) {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(appContext.getString(R.string.key_shared_preferences_name), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(KEY_IS_DATA_INSERTED_IN_DB, isDataInsertedInDb).apply();
    }

    public boolean isDataInsertedInDb(@NonNull Context appContext) {
        SharedPreferences prefs = appContext.getSharedPreferences(appContext.getString(R.string.key_shared_preferences_name), Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_IS_DATA_INSERTED_IN_DB, false);
    }
}
