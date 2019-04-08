package com.codingexercise.flickr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GalleryImage implements Parcelable {

    public final static Creator<GalleryImage> CREATOR = new Creator<GalleryImage>() {


        @SuppressWarnings({
                "unchecked"
        })
        public GalleryImage createFromParcel(Parcel in) {
            return new GalleryImage(in);
        }

        public GalleryImage[] newArray(int size) {
            return (new GalleryImage[size]);
        }

    };
    @SerializedName("ImageUrls")
    @Expose
    private ImageUrls imageUrls;
    @SerializedName("OriginalHeight")
    @Expose
    private Integer originalHeight;
    @SerializedName("OriginalWidth")
    @Expose
    private Integer originalWidth;

    protected GalleryImage(Parcel in) {
        this.imageUrls = ((ImageUrls) in.readValue((ImageUrls.class.getClassLoader())));
        this.originalHeight = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.originalWidth = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public GalleryImage() {
    }

    public ImageUrls getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(Integer originalHeight) {
        this.originalHeight = originalHeight;
    }

    public Integer getOriginalWidth() {
        return originalWidth;
    }

    public void setOriginalWidth(Integer originalWidth) {
        this.originalWidth = originalWidth;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(imageUrls);
        dest.writeValue(originalHeight);
        dest.writeValue(originalWidth);
    }

    public int describeContents() {
        return 0;
    }

}
