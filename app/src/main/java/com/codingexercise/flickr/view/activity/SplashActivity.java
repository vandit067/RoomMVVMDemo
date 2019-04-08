package com.codingexercise.flickr.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.codingexercise.flickr.R;
import com.codingexercise.flickr.database.entity.Photos;
import com.codingexercise.flickr.model.ResponseDataWrapper;
import com.codingexercise.flickr.preference.FlickrSharedPreference;
import com.codingexercise.flickr.viewmodel.SplashViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class SplashActivity extends BaseActivity {

    /**
     * {@link com.codingexercise.flickr.model.ResponseDataWrapper} observer which will observe event through {@link androidx.lifecycle.LiveData} and update UI accordingly.
     */
    @SuppressWarnings("unchecked")
    private final Observer<ResponseDataWrapper> mResponseDataWrapperObserver = responseDataWrapper -> {
        //Handle UI
        if (responseDataWrapper == null || responseDataWrapper.getThrowable() != null) {
            showError(R.string.message_no_data_available);
            return;
        }
        List<Long> entriesList = (List<Long>) responseDataWrapper.getResponse();
        if (entriesList == null || entriesList.isEmpty()) {
            showError(R.string.message_no_data_available);
            return;
        }
        FlickrSharedPreference.INSTANCE.saveIsDataInsertedInDb(getApplication(), true);
        navigateToHomeScreen();
    };
    private SplashViewModel mSplashViewModel;
    /**
     * {@link com.codingexercise.flickr.model.ResponseDataWrapper} observer which will observe event through {@link androidx.lifecycle.LiveData} and update UI accordingly.
     */
    @SuppressWarnings("unchecked")
    private final Observer<ResponseDataWrapper> mPhotosListWrapperObserver = responseDataWrapper -> {
        //Handle UI
        if (responseDataWrapper == null || responseDataWrapper.getThrowable() != null) {
            showError(R.string.message_no_data_available);
            return;
        }
        List<Photos> photosList = (List<Photos>) responseDataWrapper.getResponse();
        if (photosList == null || photosList.isEmpty()) {
            showError(R.string.message_no_data_available);
            return;
        }
        mSplashViewModel.insertData(photosList);
    };

    private final Observer<Boolean> mTimerObserver = aBoolean -> navigateToHomeScreen();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // We are not creating instance of ViewModel class here.
        this.mSplashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        this.mSplashViewModel.getPhotosListObserver().observe(this, this.mPhotosListWrapperObserver);
        this.mSplashViewModel.getDataObserver().observe(this, this.mResponseDataWrapperObserver);
    }

    @Override
    protected void setUp() {
        if(!isNetworkConnected()){
            showSnackBarWithAction(getString(R.string.error_network_check), "Got it!", aBoolean -> finish());
            return;
        }
        if (FlickrSharedPreference.INSTANCE.isDataInsertedInDb(getApplication())) {
            mSplashViewModel.executeTimer(this.mTimerObserver);
            return;
        }
        mSplashViewModel.retrievePhotosFromAssets("CodeChallengeImages.json");
    }

    private void navigateToHomeScreen() {
        Intent intent = new Intent(this, PhotosActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mSplashViewModel != null) {
            this.mSplashViewModel = null;
        }
    }
}
