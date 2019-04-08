package com.codingexercise.flickr.repository;

import android.app.Application;

import com.codingexercise.flickr.database.PhotosDatabase;
import com.codingexercise.flickr.database.dao.PhotosDao;
import com.codingexercise.flickr.database.entity.Photos;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class PhotosRepository implements PhotosDataSource {
    private PhotosDao photosDao;

    public PhotosRepository(Application application) {
        PhotosDatabase photosDatabase = PhotosDatabase.getInstance(application);
        photosDao = photosDatabase.photosDao();
    }

    public Single<List<Photos>> getAllPhotos() {
        return photosDao.getPhotos();
    }

    @Override
    public Single<List<Photos>> getFavoritePhotos() {
        return photosDao.getFavoritePhotos();
    }

    @Override
    public void updateFavorite(int photoId, boolean isFavorite) {
        photosDao.updateFavorite(photoId, isFavorite);
    }

    @Override
    public Single<Integer> update(Photos photos) {
        return photosDao.update(photos);
    }

    @Override
    public Single<List<Long>> insert(@NonNull List<Photos> photos) {
        return photosDao.insert(photos);
    }

}
