package com.codingexercise.flickr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageData implements Parcelable {

    public final static Creator<ImageData> CREATOR = new Creator<ImageData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ImageData createFromParcel(Parcel in) {
            return new ImageData(in);
        }

        public ImageData[] newArray(int size) {
            return (new ImageData[size]);
        }

    };
    @SerializedName("GalleryImages")
    @Expose
    private List<GalleryImage> galleryImages = null;
    @SerializedName("Count")
    @Expose
    private Integer count;

    protected ImageData(Parcel in) {
        in.readList(this.galleryImages, (GalleryImage.class.getClassLoader()));
        this.count = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public ImageData() {
    }

    public List<GalleryImage> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(List<GalleryImage> galleryImages) {
        this.galleryImages = galleryImages;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(galleryImages);
        dest.writeValue(count);
    }

    public int describeContents() {
        return 0;
    }

}
