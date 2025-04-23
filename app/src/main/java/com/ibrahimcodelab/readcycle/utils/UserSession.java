package com.ibrahimcodelab.readcycle.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ibrahimcodelab.readcycle.models.UserResponse;

public class UserSession {

    public static final String NAME = "session";
    public static final String LOG_IN_KEY = "isLogin";
    public static final String KEY_USER = "user";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public UserSession(Context context){
        this.sharedPreferences = context.getSharedPreferences(NAME, MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void putUser(UserResponse userResponse){
        editor.putString(KEY_USER, new Gson().toJson(userResponse));
        editor.commit();
    }

    public UserResponse getUser(){
        return new Gson().fromJson(sharedPreferences.getString(KEY_USER, null), UserResponse.class);
    }

    public void setFirstTime(boolean b){
        editor.putBoolean(LOG_IN_KEY,b);
        editor.commit();
    }

    public boolean isFirstTime(){
        return sharedPreferences.getBoolean(LOG_IN_KEY, true);
    }
}
