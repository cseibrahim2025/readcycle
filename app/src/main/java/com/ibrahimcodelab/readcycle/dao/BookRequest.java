package com.ibrahimcodelab.readcycle.dao;

import com.google.gson.annotations.SerializedName;

public class BookRequest {

    @SerializedName("user_id")
    private int userId;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("photo_path")
    private String photoPath;

    public BookRequest(String title, String photoPath, int categoryId, int userId, String description) {
        this.title = title;
        this.photoPath = null;
        this.categoryId = categoryId;
        this.userId = userId;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
