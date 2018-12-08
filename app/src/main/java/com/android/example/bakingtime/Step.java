package com.android.example.bakingtime;

import android.os.Parcel;
import android.os.Parcelable;

class Step implements Parcelable {
    private long Id;
    private String ShortDescription;
    private String Description;
    private String VideoURL;
    private String ThumbnailURL;

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel parcel) {
            return new Step(parcel);
        }

        @Override
        public Step[] newArray(int i) {
            return new Step[i];
        }
    };

    Step() {
        Id = -1;
        ShortDescription = "";
        Description = "";
        VideoURL = "";
        ThumbnailURL = "";
    }

    private Step(Parcel parcel) {
        Id = parcel.readLong();
        ShortDescription = parcel.readString();
        Description = parcel.readString();
        VideoURL = parcel.readString();
        ThumbnailURL = parcel.readString();
    }

    long getId() {
        return Id;
    }

    void setId(long value) {
        Id = value;
    }

    String getShortDescription() {
        return ShortDescription;
    }

    void setShortDescription(String value) {
        ShortDescription = value;
    }

    String getDescription() {
        return Description;
    }

    void setDescription(String value) {
        Description = value;
    }

    String getVideoURL() {
        return VideoURL;
    }

    void setVideoURL(String value) {
        VideoURL = value;
    }

    String getThumbnailURL() {
        return ThumbnailURL;
    }

    void setThumbnailURL(String value) {
        ThumbnailURL = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(Id);
        parcel.writeString(ShortDescription);
        parcel.writeString(Description);
        parcel.writeString(VideoURL);
        parcel.writeString(ThumbnailURL);
    }
}
