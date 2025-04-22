package com.ibrahimcodelab.readcycle.services;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ibrahimcodelab.readcycle.dao.LoginRequest;
import com.ibrahimcodelab.readcycle.dao.RegisterRequest;
import com.ibrahimcodelab.readcycle.models.UserResponse;
import com.ibrahimcodelab.readcycle.networking.ApiClient;
import com.ibrahimcodelab.readcycle.networking.ApiService;
import com.ibrahimcodelab.readcycle.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationService {

    private final ApiService apiService;

    public AuthenticationService() {
        this.apiService = ApiClient.getClient(Constants.BASE_URL).create(ApiService.class);
    }

    public void register(Context context, RegisterRequest registerRequest,
                         AuthenticationServiceCallback authenticationServiceCallback) {
        apiService.register(registerRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                Toast.makeText(context, "response code:" + response.code(), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String token = response.body().getToken();
                    authenticationServiceCallback.onSuccess(token);
                } else {
                    authenticationServiceCallback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                authenticationServiceCallback.onFailure();
            }
        });
    }

    public void login(Context context, LoginRequest loginRequest,
                      AuthenticationServiceCallback authenticationServiceCallback) {
        apiService.login(loginRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String token = response.body().getToken();
                    Toast.makeText(context, "login is successful", Toast.LENGTH_SHORT).show();
                    authenticationServiceCallback.onSuccess(token);
                } else {
                    Toast.makeText(context, "login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, "login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
