package com.shashank.platform.saloon.ui;

import static com.shashank.platform.saloon.application.MySaloonApplication.sharedPreferenceClass;
import static com.shashank.platform.saloon.constant.ApiConst.ONE_TIME_LOGIN;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.platform.saloon.R;
import com.shashank.platform.saloon.constant.SharedPreferenceClass;


public class SplashActivity extends AppCompatActivity {

    Button join_us;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        join_us = findViewById(R.id.join_us);
        login = findViewById(R.id.login);
        join_us.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);
        });
        login.setOnClickListener(view -> {
            boolean firstTimeLogin = sharedPreferenceClass.getBoolean(ONE_TIME_LOGIN);
            if (firstTimeLogin) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), NewLoginActivity.class);
                startActivity(intent);
            }

        });
    }
}
