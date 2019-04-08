package com.codingexercise.flickr.viewmodel;

import android.app.Application;

import com.codingexercise.flickr.database.entity.Photos;
import com.codingexercise.flickr.model.GalleryImage;
import com.codingexercise.flickr.model.ImageData;
import com.codingexercise.flickr.model.ResponseDataWrapper;
import com.codingexercise.flickr.repository.PhotosRepository;
import com.codingexercise.flickr.util.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashViewModel extends BaseViewModel {

    private MutableLiveData<ResponseDataWrapper> mResponseData;
    private MutableLiveData<ResponseDataWrapper> mPhotosListData;
    private PhotosRepository mPhotosRepository;

    /**
     * Constructor with application instance and initialize {@link io.reactivex.disposables.CompositeDisposable}
     *
     * @param application instance of application
     */
    public SplashViewModel(@NonNull Application application) {
        super(application);
        this.mPhotosRepository = new PhotosRepository(application);
    }

    @UiThread
    public MutableLiveData<ResponseDataWrapper> getDataObserver() {
        if (mResponseData == null) {
            this.mResponseData = new MutableLiveData<>();
        }
        return this.mResponseData;
    }

    @UiThread
    public MutableLiveData<ResponseDataWrapper> getPhotosListObserver() {
        if (mPhotosListData == null) {
            this.mPhotosListData = new MutableLiveData<>();
        }
        return this.mPhotosListData;
    }

    @WorkerThread
    private List<Photos> getPhotosListFromJson(@NonNull String fileName) {
        ImageData imageData = JsonUtils.getImageDataFromFile(getAppContext(), fileName);
        if (imageData == null || imageData.getCount() < 1) {
            return Collections.emptyList();
        }
        List<Photos> photosList = new ArrayList<>();
        for (GalleryImage galleryImage : imageData.getGalleryImages()) {
            photosList.add(new Photos(galleryImage.getImageUrls().getSizeLarge(),
                    galleryImage.getImageUrls().getSizeThumb(), galleryImage.getOriginalHeight(),
                    galleryImage.getOriginalWidth()));
        }
        return photosList;
    }

    @WorkerThread
    private Single<List<Photos>> getPhotosList(@NonNull String fileName) {
        return Single.just(getPhotosListFromJson(fileName));
    }

    @WorkerThread
    public void retrievePhotosFromAssets(@NonNull String fileName) {
        addDisposable(this.getPhotosList(fileName)
                .subscribeOn(getAppRxSchedulers().runOnBackground())
                .observeOn(getAppRxSchedulers().androidThread())
                .subscribe(photosList -> mPhotosListData.postValue(new ResponseDataWrapper<List<Photos>>(photosList)),
                        throwable -> mPhotosListData.postValue(new ResponseDataWrapper(throwable))));
    }

    @WorkerThread
    public void insertData(@NonNull List<Photos> photosList) {
        addDisposable(this.mPhotosRepository.insert(photosList)
                .subscribeOn(getAppRxSchedulers().runOnBackground())
                .observeOn(getAppRxSchedulers().androidThread())
                .subscribe(longs -> mResponseData.postValue(new ResponseDataWrapper<>(longs)),
                        throwable -> mResponseData.postValue(new ResponseDataWrapper(throwable))));
    }

    @WorkerThread
    public void executeTimer(@NonNull Observer<Boolean> observer){
        addDisposable(Single.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> observer.onChanged(true), throwable -> observer.onChanged(false)));
    }
}
