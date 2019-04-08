package com.codingexercise.flickr.viewmodel;

import android.app.Application;
import android.util.Log;

import com.codingexercise.flickr.database.entity.Photos;
import com.codingexercise.flickr.model.ResponseDataWrapper;
import com.codingexercise.flickr.repository.PhotosRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.Completable;

/**
 * Class used for handle the business logic for {@link com.codingexercise.flickr.view.fragment.FavoritePhotosFragment}
 */
public class FavoritePhotosViewModel extends BaseViewModel {

    private MutableLiveData<ResponseDataWrapper> mPhotosData;
    private PhotosRepository mPhotosRepository;

    public FavoritePhotosViewModel(@NonNull Application application) {
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
        addDisposable(mPhotosRepository.getFavoritePhotos()
                .subscribeOn(getAppRxSchedulers().runOnBackground())
                .observeOn(getAppRxSchedulers().androidThread())
                .subscribe(photos -> mPhotosData.postValue(new ResponseDataWrapper<List<Photos>>(photos))
                        , throwable -> mPhotosData.postValue(new ResponseDataWrapper(throwable))));

    }

    @WorkerThread
    public void updateFavoritePhotoDetailsInDb(@NonNull Photos photos, @NonNull Observer<Photos> observer) {
        addDisposable(Completable.fromAction(() -> mPhotosRepository.updateFavorite(photos.getId(), photos.isFavorite()))
                .subscribeOn(getAppRxSchedulers().runOnBackground())
                .observeOn(getAppRxSchedulers().androidThread())
                .subscribe(() -> observer.onChanged(photos),
                        throwable -> Log.e(PhotosViewModel.class.getSimpleName(), "Unable to remove photo from favorite list", throwable)));
    }

}
