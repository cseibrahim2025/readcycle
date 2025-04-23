package com.ibrahimcodelab.readcycle.activities.welcome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.activities.auth.LoginActivity;
import com.ibrahimcodelab.readcycle.utils.UserSession;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            Class intentClass = new UserSession(this).isFirstTime() ? WelcomeActivity.class : LoginActivity.class;
            Intent intent = new Intent(SplashActivity.this, intentClass);
            startActivity(intent);
            finish();
        }, 1500);
    }
}