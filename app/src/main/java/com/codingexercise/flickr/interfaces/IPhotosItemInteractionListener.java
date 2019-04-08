package com.codingexercise.flickr.interfaces;

import com.codingexercise.flickr.database.entity.Photos;

import androidx.annotation.NonNull;

public interface IPhotosItemInteractionListener {
    void onFavoritePhotoClick(int position, @NonNull Photos photos);
}
