package com.ibrahimcodelab.readcycle.services;

public interface AuthenticationServiceCallback {
    void onSuccess(String token);
    void onFailure();
}
