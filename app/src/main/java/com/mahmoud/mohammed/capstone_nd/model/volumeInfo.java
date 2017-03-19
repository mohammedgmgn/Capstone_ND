package com.mahmoud.mohammed.capstone_nd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by siko on 3/19/2017.
 */

public class volumeInfo implements Parcelable {
    private String title;
    private String publishedDate;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    }

    public volumeInfo() {
    }

    protected volumeInfo(Parcel in) {
        this.title = in.readString();
        this.publishedDate = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<volumeInfo> CREATOR = new Parcelable.Creator<volumeInfo>() {
        @Override
        public volumeInfo createFromParcel(Parcel source) {
            return new volumeInfo(source);
        }

        @Override
        public volumeInfo[] newArray(int size) {
            return new volumeInfo[size];
        }
    };
}
