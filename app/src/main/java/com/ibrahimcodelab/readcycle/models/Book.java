package com.ibrahimcodelab.readcycle.models;

import android.text.TextUtils;
import android.view.TextureView;

import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("photo_path")
    public String photoPath;

    @SerializedName("user")
    public User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoPath() {
        if(TextUtils.isEmpty(photoPath)){
            photoPath = "https://i.imgur.com/7.jpg";
        }
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
