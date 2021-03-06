package com.mahmoud.mohammed.capstone_nd.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by siko on 3/19/2017.
 */

public class Book implements Parcelable {
    private String title;
    private String publishedDate;
    private String description;
    private String id;
    private String imageUrl;
    private Drawable imagrece;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Drawable getImagrece() {
        return imagrece;
    }

    public void setImagrece(Drawable imagrece) {
        this.imagrece = imagrece;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.publishedDate);
        dest.writeString(this.description);
        dest.writeString(this.id);
        dest.writeString(this.imageUrl);
        dest.writeParcelable((Parcelable) this.imagrece, flags);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.title = in.readString();
        this.publishedDate = in.readString();
        this.description = in.readString();
        this.id = in.readString();
        this.imageUrl = in.readString();
        this.imagrece = in.readParcelable(Drawable.class.getClassLoader());
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}