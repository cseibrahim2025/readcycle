package com.ibrahimcodelab.readcycle.services;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ibrahimcodelab.readcycle.models.CategoryResponse;
import com.ibrahimcodelab.readcycle.networking.ApiClient;
import com.ibrahimcodelab.readcycle.networking.ApiService;
import com.ibrahimcodelab.readcycle.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookService {

    private final ApiService apiService;

    public BookService() {
        this.apiService = ApiClient.getClient(Constants.BASE_URL).create(ApiService.class);
    }

    public void fetchCategories(Context context, BookServiceCallback bookServiceCallback) {
        apiService.getCategoriesWithBooks().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<CategoryResponse>> call, @NonNull Response<List<CategoryResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookServiceCallback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Toast.makeText(context, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
