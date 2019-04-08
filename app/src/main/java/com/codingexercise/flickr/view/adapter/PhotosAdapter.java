package com.codingexercise.flickr.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.codingexercise.flickr.R;
import com.codingexercise.flickr.database.entity.Photos;
import com.codingexercise.flickr.interfaces.IPhotosItemInteractionListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {

    /**
     * ViewTypes serve as a mapping point to which layout should be inflated
     */
    private RequestOptions mRequestOptions;
    private ViewPropertyTransition.Animator animationObject = view -> {
        view.setAlpha(0f);
        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnim.setDuration(500);
        fadeAnim.start();
    };

    @NonNull
    private List<Photos> mPhotosList;
    private IPhotosItemInteractionListener mIPhotosItemInteractionListener;


    public PhotosAdapter(@NonNull List<Photos> photosList, @NonNull IPhotosItemInteractionListener iPhotosItemInteractionListener) {
        this.mPhotosList = photosList;
        this.mIPhotosItemInteractionListener = iPhotosItemInteractionListener;
        this.mRequestOptions = new RequestOptions().centerCrop()
                .placeholder(R.drawable.ic_flickr)
                .error(R.drawable.ic_flickr)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
    }


    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photos, parent, false);
        return new PhotosViewHolder(itemView);
    }

    @SuppressLint({"DefaultLocale", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder holder, int position) {
        Photos photos = mPhotosList.get(position);
        if (photos == null) {
            return;
        }
        if (!TextUtils.isEmpty(photos.getImageUrlThumb())) {
            Glide.with(holder.itemView.getContext().getApplicationContext()).asBitmap().load(photos.getImageUrlThumb())
                    .transition(GenericTransitionOptions.with(animationObject))
                    .apply(this.mRequestOptions)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            Palette.from(resource).generate(palette -> {
                                if (palette == null) {
                                    return;
                                }
                                Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                                if (swatch == null) {
                                    return;
                                }
                                holder.tvSize.setTextColor(palette.getMutedColor(swatch.getRgb()));
                            });
                            return false;
                        }
                    })
                    .into(holder.ivPhoto);
        }
        holder.ivFavorite.setImageResource(photos.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_non_favorite);
        holder.tvSize.setText(String.format("%d X %d", photos.getImageHeight(), photos.getImageWidth()));
    }

    @Override
    public int getItemCount() {
        return this.mPhotosList.size();
    }

    public Photos getItemAtPosition(int position) {
        return mPhotosList.get(position);
    }

    /**
     * Add item at position in list.
     *
     * @param photosList new list of {@link Photos}
     */
    public void addItems(@NonNull List<Photos> photosList) {
        mPhotosList.addAll(photosList);
        notifyItemRangeInserted(getItemCount(), mPhotosList.size() - 1);
    }

    /**
     * Remove item at position in list.
     *
     * @param photos insance of {@link Photos}
     */
    public void removeItem(@NonNull Photos photos) {
        notifyItemRemoved(mPhotosList.indexOf(photos));
        mPhotosList.remove(photos);
    }

    // View holder which will display list content
    class PhotosViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_photos_iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.item_photos_iv_favorite)
        ImageView ivFavorite;
        @BindView(R.id.item_photos_tv_size)
        TextView tvSize;

        PhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.item_photos_iv_favorite)
        void onFavoriteItemClick() {
            if (mIPhotosItemInteractionListener == null || getAdapterPosition() < 0 || getAdapterPosition() > getItemCount() - 1) {
                return;
            }
            Photos photos = mPhotosList.get(getAdapterPosition());
            photos.setFavorite(!photos.isFavorite());
            mIPhotosItemInteractionListener.onFavoritePhotoClick(getAdapterPosition(), photos);
        }
    }
}
