package com.codingexercise.flickr.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.codingexercise.flickr.model.ImageData;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class JsonUtils {

    @Nullable
    public static ImageData getImageDataFromFile(@NonNull Context appContext, @NonNull String fileName) {
        String jsonData = parseFileToString(appContext, fileName);
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        return new Gson().fromJson(jsonData, ImageData.class);
    }

    private static String parseFileToString(@NonNull Context appContext, @NonNull String filename) {
        try {
            InputStream stream = appContext.getAssets().open(filename);
            int size = stream.available();
            byte[] bytes = new byte[size];
            stream.read(bytes);
            stream.close();
            return new String(bytes);
        } catch (IOException e) {
            Log.i(JsonUtils.class.getSimpleName(), "IOException: " + e.getMessage());
        }
        return null;
    }
}
