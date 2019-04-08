package com.codingexercise.flickr.view.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.codingexercise.flickr.R;
import com.codingexercise.flickr.database.entity.Photos;
import com.codingexercise.flickr.interfaces.IPhotosItemInteractionListener;
import com.codingexercise.flickr.model.ResponseDataWrapper;
import com.codingexercise.flickr.view.adapter.PhotosAdapter;
import com.codingexercise.flickr.viewmodel.PhotosViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class is used for perform resource search.
 */
public class PhotosFragment extends BaseFragment implements IPhotosItemInteractionListener {

    @BindView(R.id.fragment_photos_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.fragment_photos_rv_homes)
    RecyclerView mRvPhotos;
    @BindView(R.id.fragment_photos_rl_content)
    RelativeLayout mRlContent;
    @BindView(R.id.fragment_photos_iv_background)
    ImageView mIvBackground;
    @BindView(R.id.fragment_photos_rl_error)
    RelativeLayout mRlErrorView;

    private PhotosViewModel mPhotosViewModel;
    private PhotosAdapter mPhotosAdapter;
    private SnapHelper mSnapHelper;
    private LinearLayoutManager mLinearLayoutManager;
    /**
     * ViewTypes serve as a mapping point to which layout should be inflated
     */
    private RequestOptions mRequestOptions = new RequestOptions().centerCrop()
            .placeholder(R.drawable.ic_flickr)
            .error(R.drawable.ic_flickr)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH);
    private ViewPropertyTransition.Animator animationObject = view -> {
        view.setAlpha(0f);
        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnim.setDuration(500);
        fadeAnim.start();
    };

    /**
     * {@link com.codingexercise.flickr.model.ResponseDataWrapper} observer which will observe event through {@link androidx.lifecycle.LiveData} and update UI accordingly.
     */
    @SuppressWarnings("unchecked")
    private final Observer<ResponseDataWrapper> mResponseDataWrapperObserver = new Observer<ResponseDataWrapper>() {
        @Override
        public void onChanged(@Nullable ResponseDataWrapper responseDataWrapper) {
            //Handle UI
            showContent(mProgressBar, mRlContent);
            if (responseDataWrapper == null || responseDataWrapper.getThrowable() != null) {
                showErrorView(mProgressBar, mRlContent, mRlErrorView);
                return;
            }
            List<Photos> photosList = (List<Photos>) responseDataWrapper.getResponse();
            if (photosList == null || photosList.isEmpty()) {
                showErrorView(mProgressBar, mRlContent, mRlErrorView);
                return;
            }
            setBackgroundImage(photosList.get(0).getImageUrlLarge());
            mPhotosAdapter.addItems(photosList);
        }
    };

    private RecyclerView.OnScrollListener mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                View centerView = mSnapHelper.findSnapView(mLinearLayoutManager);
                if (centerView == null) {
                    return;
                }
                int pos = mLinearLayoutManager.getPosition(centerView);
                if (mRvPhotos.getAdapter() == null || pos < 0 || pos > mRvPhotos.getAdapter().getItemCount()) {
                    return;
                }
                setBackgroundImage(((PhotosAdapter) mRvPhotos.getAdapter()).getItemAtPosition(pos).getImageUrlLarge());
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    // Create new instance of PhotosFragment
    public static PhotosFragment newInstance() {
        return new PhotosFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We are not creating instance of ViewModel class here.
        this.mPhotosViewModel = ViewModelProviders.of(this).get(PhotosViewModel.class);
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        this.mPhotosViewModel.getPhotosDataObserver().observe(this, this.mResponseDataWrapperObserver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        return view;
    }

    /**
     * Initialize Ui Component
     */
    private void initUI() {
        this.mLinearLayoutManager = new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.HORIZONTAL, false);
        this.mRvPhotos.setLayoutManager(this.mLinearLayoutManager);
        addItemDividerDecorationToRecyclerView(this.mRvPhotos, this.mLinearLayoutManager.getOrientation());
        this.mPhotosAdapter = new PhotosAdapter(new ArrayList<>(), this);
        this.mRvPhotos.setAdapter(this.mPhotosAdapter);
        mRvPhotos.addOnScrollListener(mRecyclerViewOnScrollListener);
        this.mSnapHelper = new PagerSnapHelper();
        this.mSnapHelper.attachToRecyclerView(this.mRvPhotos);
    }


    /**
     * Get data from the database
     */
    private void retrievePhotos() {
        showProgress(this.mProgressBar, this.mRlContent);
        this.mPhotosViewModel.retrievePhotos();
    }

    private void setBackgroundImage(@NonNull String imageUrl) {
        Glide.with(PhotosFragment.this).load(imageUrl)
                .transition(GenericTransitionOptions.with(animationObject))
                .apply(mRequestOptions)
                .into(mIvBackground);
    }

    /**
     * Initialize your UI components here.
     *
     * @param view current view
     */
    @Override
    protected void setUp(@NonNull View view) {
        this.initUI();
        this.retrievePhotos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mPhotosViewModel != null) {
            this.mPhotosViewModel = null;
        }
    }

    @Override
    public void onFavoritePhotoClick(int position, @NonNull Photos photos) {
        this.mPhotosAdapter.notifyItemChanged(position);
        this.mPhotosViewModel.updateFavoritePhotoDetailsInDb(photos);
    }
}
