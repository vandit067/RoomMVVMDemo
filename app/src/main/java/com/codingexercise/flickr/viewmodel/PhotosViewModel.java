package com.codingexercise.flickr.viewmodel;

import android.app.Application;

import com.codingexercise.flickr.database.entity.Photos;
import com.codingexercise.flickr.model.ResponseDataWrapper;
import com.codingexercise.flickr.repository.PhotosRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Completable;

/**
 * Class used for handle the business logic for {@link com.codingexercise.flickr.view.fragment.PhotosFragment}
 */
public class PhotosViewModel extends BaseViewModel {

    private MutableLiveData<ResponseDataWrapper> mPhotosData;
    private PhotosRepository mPhotosRepository;

    public PhotosViewModel(@NonNull Application application) {
        super(application);
        this.mPhotosRepository = new PhotosRepository(application);
    }


    @UiThread
    public MutableLiveData<ResponseDataWrapper> getPhotosDataObserver() {
        if (mPhotosData == null) {
            this.mPhotosData = new MutableLiveData<>();
        }
        return this.mPhotosData;
    }

    @WorkerThread
    public void retrievePhotos() {
        addDisposable(mPhotosRepository.getAllPhotos()
                .subscribeOn(getAppRxSchedulers().runOnBackground())
                .observeOn(getAppRxSchedulers().androidThread())
                .subscribe(photos -> mPhotosData.postValue(new ResponseDataWrapper<List<Photos>>(photos))
                        , throwable -> mPhotosData.postValue(new ResponseDataWrapper(throwable))));

    }

    @WorkerThread
    public void updateFavoritePhotoDetailsInDb(@NonNull Photos photos) {
        addDisposable(Completable.fromAction(() -> mPhotosRepository.updateFavorite(photos.getId(), photos.isFavorite()))
                .subscribeOn(getAppRxSchedulers().runOnBackground())
                .observeOn(getAppRxSchedulers().androidThread())
                .subscribe());
    }

}
