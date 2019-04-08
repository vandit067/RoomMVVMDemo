package com.codingexercise.flickr.database;

import android.content.Context;

import com.codingexercise.flickr.database.dao.PhotosDao;
import com.codingexercise.flickr.database.entity.Photos;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Room database that contains notes table.
 */
@Database(entities = {Photos.class}, version = 1, exportSchema = false)
public abstract class PhotosDatabase extends RoomDatabase {

    private static PhotosDatabase INSTANCE;

    public static synchronized PhotosDatabase getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PhotosDatabase.class, "Photos.db")
                    .build();
        }
        return INSTANCE;
    }

    public abstract PhotosDao photosDao();
}
