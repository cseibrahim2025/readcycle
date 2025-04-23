package com.ibrahimcodelab.readcycle.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ibrahimcodelab.readcycle.dao.LoginRequest;
import com.ibrahimcodelab.readcycle.dao.RegisterRequest;
import com.ibrahimcodelab.readcycle.models.UserResponse;
import com.ibrahimcodelab.readcycle.networking.ApiClient;
import com.ibrahimcodelab.readcycle.networking.ApiService;
import com.ibrahimcodelab.readcycle.utils.Constants;
import com.ibrahimcodelab.readcycle.utils.UserSession;

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
        apiService.register(registerRequest).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                Toast.makeText(context, "response code:" + response.code(), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    authenticationServiceCallback.onSuccess();
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
        apiService.login(loginRequest).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    UserResponse userResponse = response.body();
                    new UserSession(context).putUser(userResponse);
                    authenticationServiceCallback.onSuccess();
                } else {
                    Toast.makeText(context, "login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, "login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
