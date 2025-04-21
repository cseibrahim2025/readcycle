package com.ibrahimcodelab.readcycle.services;

import androidx.annotation.NonNull;

import com.ibrahimcodelab.readcycle.dao.RegisterRequest;
import com.ibrahimcodelab.readcycle.models.UserResponse;
import com.ibrahimcodelab.readcycle.networking.ApiClient;
import com.ibrahimcodelab.readcycle.networking.AuthApi;
import com.ibrahimcodelab.readcycle.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationService {

    private final AuthApi authApi;

    public AuthenticationService() {
        this.authApi = ApiClient.getClient(Constants.BASE_URL).create(AuthApi.class);
    }

    public void register(RegisterRequest registerRequest, AuthenticationServiceCallback authenticationServiceCallback){
        authApi.register(registerRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
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

}
