package com.ibrahimcodelab.readcycle.networking;

import com.ibrahimcodelab.readcycle.dao.BookRequest;
import com.ibrahimcodelab.readcycle.dao.LoginRequest;
import com.ibrahimcodelab.readcycle.dao.RegisterRequest;
import com.ibrahimcodelab.readcycle.models.Category;
import com.ibrahimcodelab.readcycle.models.CategoryResponse;
import com.ibrahimcodelab.readcycle.dao.SwapRequest;
import com.ibrahimcodelab.readcycle.models.SwapResponse;
import com.ibrahimcodelab.readcycle.models.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    Call<UserResponse> register(@Body RegisterRequest request);

    @POST("login")
    Call<UserResponse> login(@Body LoginRequest request);

    @POST("logout")
    Call<Void> logout(@Header("Authorization") String token);

    @GET("categories")
    Call<ApiResponse<List<Category>>> getCategories();

    @GET("categories-with-books")
    Call<List<CategoryResponse>> getCategoriesWithBooks();

    @Headers("Accept: application/json")
    @POST("books")
    Call<ApiResponse<Object>> createBook(@Body BookRequest bookRequest);

    @Headers("Accept: application/json")
    @POST("swaps")
    Call<ApiResponse<SwapResponse>> createSwapRequest(@Body SwapRequest request);

    @GET("swaps")
    Call<ApiResponse<List<SwapResponse.SwapRequestData>>> getAllSwapRequests();

}
