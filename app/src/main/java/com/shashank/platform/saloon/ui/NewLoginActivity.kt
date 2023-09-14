package com.shashank.platform.saloon.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Secure
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.shashank.platform.saloon.BR
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.application.MySaloonApplication.Companion.sharedPreferenceClass
import com.shashank.platform.saloon.base.BaseActivity
import com.shashank.platform.saloon.constant.ApiConst.ACCESS_TOKEN
import com.shashank.platform.saloon.constant.ApiConst.ID
import com.shashank.platform.saloon.constant.ApiConst.ONE_TIME_LOGIN
import com.shashank.platform.saloon.constant.ApiConst.USER_NAME
import com.shashank.platform.saloon.databinding.ActivityNewLoginBinding
import com.shashank.platform.saloon.model.LoginData
import com.shashank.platform.saloon.viewmodel.LoginViewModel
import java.util.regex.Pattern


class NewLoginActivity : BaseActivity<ActivityNewLoginBinding, LoginViewModel>() {
    private lateinit var mLoginbinding: ActivityNewLoginBinding
    private lateinit var mViewModel: LoginViewModel
    private lateinit var mDialog: SweetAlertDialog//declare this dialog globally
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    var token: String? = null

    override fun getBindingVariable(): Int {
        return BR.loginViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_new_login
    }

    override fun getViewModel(): LoginViewModel? {
        mViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        return mViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_new_login)
        mLoginbinding = getViewDataBinding()
        mLoginbinding.loginViewModel = mViewModel
        FirebaseApp.initializeApp(this@NewLoginActivity)
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                token = task.result

                //Log.d("FCMtoken", token)

            })
        mViewModel.allApplicationObserver.observe(this)
        {
            mDialog.dismiss()
            sharedPreferenceClass.setString(ACCESS_TOKEN, it.token)
            sharedPreferenceClass.setString(ID, it.id.toString())
            sharedPreferenceClass.setString(USER_NAME, it.name.toString())
            sharedPreferenceClass.setBoolean(ONE_TIME_LOGIN, true)
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        mViewModel.errorObserver.observe(this)
        {
            mDialog.dismiss()
            Toast.makeText(this@NewLoginActivity, "Wrong Credentials...", Toast.LENGTH_LONG).show()
            mLoginbinding.etEmail.requestFocus()
            mLoginbinding.etPassword.requestFocus()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = getColor(R.color.colorAccent)
        }
        supportActionBar!!.setTitle("Dashboard")
        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this, R.drawable.actionbar_color
            )
        )
        // binding = DataBindingUtil.setContentView(this, R.layout.activity_new_login)

        //  binding.back.setOnClickListener { finish() }

        mLoginbinding.btnLogin.setOnClickListener {
            if (!isValidString(mLoginbinding.etEmail.text.toString())) {
                Toast.makeText(this@NewLoginActivity, "Enter Valid Email", Toast.LENGTH_LONG).show()
            } else if (mLoginbinding.etPassword.text.toString()
                    .isEmpty() && mLoginbinding.etPassword.text.toString().isBlank()
            ) {
                Toast.makeText(this@NewLoginActivity, "Enter Password", Toast.LENGTH_LONG).show()

            } else {
                mDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
                mDialog.titleText = "Loading ..."
                mDialog.setCancelable(false)
                mDialog.show()
                /* val deviceId = Secure.getString(
                     getContentResolver(),
                     Secure.ANDROID_ID
                 )*/
                val deviceId = token
                Log.d("deviceId", "deviceId: " + deviceId)
                val loginData = LoginData(
                    mLoginbinding.etEmail.text.toString(),
                    mLoginbinding.etPassword.text.toString(), deviceId!!

                )
                mViewModel.getLogin(loginData)
            }

        }

    }

    override fun onBackPressed() {
        finish()
    }

    fun isValidString(str: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }
}