package com.codingexercise.flickr.interfaces;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.Observer;

/**
 * Class which have common ui functions to perform in most of the screen.
 */
public interface IBaseView {
    void showProgress(@NonNull View progressView, @NonNull View contentView);

    void showContent(@NonNull View progressView, @NonNull View contentView);

    void showErrorView(@NonNull View progressView, @NonNull View contentView, @NonNull View errorView);

    void showError(@NonNull String message);

    void showError(@StringRes int resId);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void showSnackBar(String message, int length);

    void showSnackBarWithAction(String message, String actionName, Observer<Boolean> obsever);

    boolean isNetworkConnected();
}
