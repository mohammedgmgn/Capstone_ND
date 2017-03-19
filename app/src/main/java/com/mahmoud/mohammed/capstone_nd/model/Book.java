package com.mahmoud.mohammed.capstone_nd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by siko on 3/19/2017.
 */

public class Book implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    private String title;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;
    private String id;

    public Book(String title, String image, String id) {
        this.title = title;
        this.imageUrl = image;
        this.id = id;
    }

    public Book(Parcel in) {
        String[] data = new String[5];

        in.readStringArray(data);
        this.title = data[0];
        this.imageUrl = data[1];
        this.id = data[4];
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.title,
                this.imageUrl,
                this.id});
    }
}