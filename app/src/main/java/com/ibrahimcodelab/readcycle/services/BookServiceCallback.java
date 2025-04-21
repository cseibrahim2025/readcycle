package com.ibrahimcodelab.readcycle.services;

import com.ibrahimcodelab.readcycle.models.CategoryResponse;

import java.util.List;

public interface BookServiceCallback {
    void onSuccess(List<CategoryResponse> categoryResponseList);
    void onFailure();
}
