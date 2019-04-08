package com.codingexercise.flickr.database.dao;

import com.codingexercise.flickr.database.entity.Photos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public interface PhotosDao {
    @Query("SELECT * FROM photos")
    Single<List<Photos>> getPhotos();

    @Query("SELECT * FROM photos WHERE is_favorite = 1")
    Single<List<Photos>> getFavoritePhotos();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> update(Photos photos);

    @Query("UPDATE photos SET is_favorite =:isFavorite WHERE id =:photoId")
    void updateFavorite(int photoId, boolean isFavorite);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insert(List<Photos> photos);
}
