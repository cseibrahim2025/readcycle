package com.ibrahimcodelab.readcycle.networking;

import com.ibrahimcodelab.readcycle.dao.LoginRequest;
import com.ibrahimcodelab.readcycle.dao.RegisterRequest;
import com.ibrahimcodelab.readcycle.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("register")
    Call<UserResponse> register(@Body RegisterRequest request);

    @POST("login")
    Call<UserResponse> login(@Body LoginRequest request);

    @POST("logout")
    Call<Void> logout(@Header("Authorization") String token);
}
