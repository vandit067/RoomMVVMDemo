package com.codingexercise.flickr.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageUrls implements Parcelable {

    public final static Creator<ImageUrls> CREATOR = new Creator<ImageUrls>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ImageUrls createFromParcel(Parcel in) {
            return new ImageUrls(in);
        }

        public ImageUrls[] newArray(int size) {
            return (new ImageUrls[size]);
        }

    };
    @SerializedName("SizeLarge")
    @Expose
    private String sizeLarge;
    @SerializedName("SizeThumb")
    @Expose
    private String sizeThumb;

    protected ImageUrls(Parcel in) {
        this.sizeLarge = ((String) in.readValue((String.class.getClassLoader())));
        this.sizeThumb = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ImageUrls() {
    }

    public String getSizeLarge() {
        return sizeLarge;
    }

    public void setSizeLarge(String sizeLarge) {
        this.sizeLarge = sizeLarge;
    }

    public String getSizeThumb() {
        return sizeThumb;
    }

    public void setSizeThumb(String sizeThumb) {
        this.sizeThumb = sizeThumb;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sizeLarge);
        dest.writeValue(sizeThumb);
    }

    public int describeContents() {
        return 0;
    }

}
