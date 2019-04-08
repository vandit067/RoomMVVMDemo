package com.codingexercise.flickr.view.activity;

import android.os.Bundle;

import com.codingexercise.flickr.R;
import com.codingexercise.flickr.view.fragment.FavoritePhotosFragment;
import com.codingexercise.flickr.view.fragment.PhotosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosActivity extends BaseActivity {

    @BindView(R.id.activity_photos_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_photos_navigation)
    BottomNavigationView mBottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_photos:
                replaceFragment(PhotosFragment.newInstance());
                return true;
            case R.id.navigation_favorite:
                replaceFragment(FavoritePhotosFragment.newInstance());
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void setUp() {
        mBottomNavigationView.setSelectedItemId(R.id.navigation_photos);
    }

}
