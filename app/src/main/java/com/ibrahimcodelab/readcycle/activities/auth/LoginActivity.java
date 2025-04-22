package com.ibrahimcodelab.readcycle.activities.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.activities.MainActivity;
import com.ibrahimcodelab.readcycle.dao.LoginRequest;
import com.ibrahimcodelab.readcycle.services.AuthenticationService;
import com.ibrahimcodelab.readcycle.services.AuthenticationServiceCallback;

import org.aviran.cookiebar2.CookieBar;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        findViewById(R.id.txt_dont_have_account).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });

        findViewById(R.id.btn_login).setOnClickListener(v -> {

            String email = Objects.requireNonNull(((TextInputLayout) findViewById(R.id.til_email)).getEditText()).getText().toString();
            String password = Objects.requireNonNull(((TextInputLayout) findViewById(R.id.til_password)).getEditText()).getText().toString();

            new AuthenticationService().login(LoginActivity.this, new LoginRequest(email, password), new AuthenticationServiceCallback() {
                @Override
                public void onSuccess(String token) {

                    CookieBar.build(LoginActivity.this)
                            .setDuration(700)
                            .setTitle("Success")
                            .setMessage("You are successfully logged in")
                            .setBackgroundColor(R.color.color_theme)
                            .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                            .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                            .setCookieListener(dismissType -> {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            })
                            .show();
                }

                @Override
                public void onFailure() {
                    CookieBar.build(LoginActivity.this)
                            .setDuration(1500)
                            .setTitle("Error")
                            .setMessage("failed to logged in")
                            .setBackgroundColor(R.color.red_600)
                            .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                            .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                            .show();
                }
            });
        });

    }
}