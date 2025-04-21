package com.ibrahimcodelab.readcycle.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.activities.MainActivity;
import com.ibrahimcodelab.readcycle.dao.LoginRequest;
import com.ibrahimcodelab.readcycle.services.AuthenticationService;
import com.ibrahimcodelab.readcycle.services.AuthenticationServiceCallback;

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
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure() {

                }
            });
        });

    }
}