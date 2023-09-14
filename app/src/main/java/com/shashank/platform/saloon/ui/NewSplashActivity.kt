package com.shashank.platform.saloon.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.application.MySaloonApplication.Companion.sharedPreferenceClass
import com.shashank.platform.saloon.constant.ApiConst.ONE_TIME_LOGIN
import com.shashank.platform.saloon.databinding.ActivityNewSplashBinding


class NewSplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_new_splash)


        val binding: ActivityNewSplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_new_splash)


        Handler().postDelayed({

            val flag: Boolean

            val applicationpreferences = PreferenceManager
                .getDefaultSharedPreferences(this@NewSplashActivity)

            val editor = applicationpreferences.edit()

            flag = applicationpreferences.getBoolean(ONE_TIME_LOGIN, false)
            // val firstTimeLogin = sharedPreferenceClass.getBoolean(ONE_TIME_LOGIN)

            if (!flag) {
                val intent = Intent(applicationContext, NewLoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }, 3000)

        binding.joinUs.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent(
                applicationContext,
                 RegistrationActivity::class.java
            )
            startActivity(intent)
        })
        binding.login.setOnClickListener(View.OnClickListener { view: View? ->
            val firstTimeLogin =
                sharedPreferenceClass.getBoolean(ONE_TIME_LOGIN)
            if (firstTimeLogin) {
                val intent = Intent(applicationContext, NewLoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }
}