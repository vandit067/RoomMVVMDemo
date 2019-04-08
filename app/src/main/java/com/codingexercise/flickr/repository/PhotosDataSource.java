package com.codingexercise.flickr.repository;

import com.codingexercise.flickr.database.entity.Photos;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public interface PhotosDataSource {

    Single<Integer> update(@NonNull Photos photos);

    Single<List<Long>> insert(@NonNull List<Photos> photos);

    Single<List<Photos>> getAllPhotos();

    Single<List<Photos>> getFavoritePhotos();

    void updateFavorite(int photoId, boolean isFavorite);
}
