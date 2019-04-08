package com.codingexercise.flickr.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codingexercise.flickr.R;
import com.codingexercise.flickr.interfaces.IBaseView;
import com.codingexercise.flickr.util.NetworkUtils;
import com.codingexercise.flickr.util.UiUtils;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

/**
 * Class which will be extend by all {@link android.app.Activity} of application which required to perform common operation specified here.
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    @Override
    @UiThread
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUp();
    }

    @UiThread
    @Override
    public void showSnackBar(String message, int length) {
        UiUtils.showSnackBar(findViewById(android.R.id.content), message, length);
    }

    @UiThread
    @Override
    public void showSnackBarWithAction(String message, String actionName, Observer<Boolean> obsever) {
        UiUtils.showSnackBarWithAction(findViewById(android.R.id.content), message, actionName, obsever);
    }

    @Override
    @UiThread
    public void showError(@NonNull String message) {
        showSnackBar(message, Snackbar.LENGTH_SHORT);
    }

    @Override
    @UiThread
    public void showError(@StringRes int resId) {
        showError(getString(resId));
    }

    @Override
    public void showMessage(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    @UiThread
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    @UiThread
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkAvailable(getApplicationContext());
    }

    @Override
    @UiThread
    public void showContent(@NonNull View progressView, @NonNull View contentView) {
        progressView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    @UiThread
    public void showProgress(@NonNull View progressView, @NonNull View contentView) {
        progressView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    @Override
    @UiThread
    public void showErrorView(@NonNull View progressView, @NonNull View contentView, @NonNull View errorView) {
        progressView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    /**
     * Replace fragment in container
     *
     * @param fragment fragment to be replace in container
     */
    public void replaceFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_photos_container, fragment).commit();
    }

    /**
     * Add fragment in to backstack
     *
     * @param fragment         instance of fragment
     * @param backFragmentName fragment name to add in to backstack
     */
    @UiThread
    public void addFragment(@NonNull Fragment fragment, @NonNull String backFragmentName) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment lastStackFragment = getLastStackedFragment();
        if (lastStackFragment == null) {
            return;
        }
        fragmentTransaction.hide(lastStackFragment);
        fragmentTransaction.add(R.id.activity_photos_container, fragment,
                fragment.getClass().getSimpleName()).addToBackStack(backFragmentName).commit();
    }

    /**
     * Get last stacked fragment from the fragment backstack.
     *
     * @return last fragment in backstack.
     */
    @UiThread
    public Fragment getLastStackedFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
            return fragmentManager.findFragmentByTag(entry.getName());
        }
        return fragmentManager.findFragmentById(R.id.activity_photos_container);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract void setUp();
}
