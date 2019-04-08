package com.codingexercise.flickr.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.codingexercise.flickr.interfaces.IBaseView;
import com.codingexercise.flickr.util.UiUtils;
import com.codingexercise.flickr.view.activity.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;

/**
 * Class which will be extend by all {@link Fragment} of application which required to perform common operation specified here.
 */
public abstract class BaseFragment extends Fragment implements IBaseView {

    private BaseActivity mActivity;
    private Unbinder mUnbinder;


    @Override
    @UiThread
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    @UiThread
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
    }

    @Override
    @UiThread
    public void showSnackBar(String message, int length) {
        if (this.mActivity != null) {
            mActivity.showSnackBar(message, length);
        }
    }

    @UiThread
    @Override
    public void showSnackBarWithAction(String message, String actionName, Observer<Boolean> obsever) {
        if (this.mActivity != null) {
            mActivity.showSnackBarWithAction(message, actionName, obsever);
        }
    }

    @Override
    @UiThread
    public void showError(@NonNull String message) {
        if (this.mActivity != null) {
            mActivity.showError(message);
        }
    }

    @Override
    @UiThread
    public void showError(int resId) {
        if (this.mActivity != null) {
            mActivity.showError(resId);
        }
    }

    @Override
    @UiThread
    public void showMessage(String message) {
        if (this.mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    @UiThread
    public void showMessage(int resId) {
        if (this.mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    @UiThread
    public boolean isNetworkConnected() {
        if (this.mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
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

    protected abstract void setUp(@NonNull View view);

    @UiThread
    void setUnBinder(Unbinder unBinder) {
        this.mUnbinder = unBinder;
    }

    @NonNull
    @UiThread
    BaseActivity getBaseActivity() {
        return this.mActivity;
    }

    /**
     * Add decoration of divider for {@link RecyclerView}
     *
     * @param recyclerView instance of {@link RecyclerView}
     * @param orientation  {@link RecyclerView.LayoutManager} orientation
     */
    @UiThread
    void addItemDividerDecorationToRecyclerView(@NonNull RecyclerView recyclerView, int orientation) {
        UiUtils.addItemDividerDecorationToRecyclerView(recyclerView, orientation);
    }

    @Override
    @UiThread
    public void onDestroy() {
        if (this.mUnbinder != null) {
            this.mUnbinder.unbind();
        }
        super.onDestroy();
    }
}
