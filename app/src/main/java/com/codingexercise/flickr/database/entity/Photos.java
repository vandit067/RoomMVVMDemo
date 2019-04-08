package com.codingexercise.flickr.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "photos")
public class Photos {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "image_url_thumb")
    private String imageUrlThumb;

    @ColumnInfo(name = "image_url_large")
    private String imageUrlLarge;

    @ColumnInfo(name = "image_height")
    private int imageHeight;

    @ColumnInfo(name = "image_width")
    private int imageWidth;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;


    public Photos(String imageUrlLarge, String imageUrlThumb, int imageHeight, int imageWidth) {
        this.imageUrlLarge = imageUrlLarge;
        this.imageUrlThumb = imageUrlThumb;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrlThumb() {
        return imageUrlThumb;
    }

    public String getImageUrlLarge() {
        return imageUrlLarge;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
