package com.ibrahimcodelab.readcycle.activities.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.ibrahimcodelab.readcycle.R;
import com.ibrahimcodelab.readcycle.dao.RegisterRequest;
import com.ibrahimcodelab.readcycle.services.AuthenticationService;
import com.ibrahimcodelab.readcycle.services.AuthenticationServiceCallback;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.txt_already_have_account).setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });

        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = Objects.requireNonNull(((TextInputLayout) findViewById(R.id.til_full_name)).getEditText()).getText().toString();
                String email = Objects.requireNonNull(((TextInputLayout) findViewById(R.id.til_email)).getEditText()).getText().toString();
                String password = Objects.requireNonNull(((TextInputLayout) findViewById(R.id.til_password)).getEditText()).getText().toString();

                RegisterRequest registerRequest = new RegisterRequest(
                        fullName,
                        "North Western University",
                        "CSE",
                        "2025",
                        email,
                        password
                );

                new AuthenticationService().register(registerRequest, new AuthenticationServiceCallback() {
                    @Override
                    public void onSuccess(String token) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });

    }
}