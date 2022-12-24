package com.example.application01.utility;

import android.os.Parcel;
import android.os.Parcelable;

public class ArtistModel  {
    private String name;
    private String img_url;
    private String id;

    public ArtistModel(String name, String img_url, String id) {
        this.name = name;
        this.img_url = img_url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
